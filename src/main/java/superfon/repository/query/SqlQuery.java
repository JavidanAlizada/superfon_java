package superfon.repository.query;

import org.springframework.stereotype.Repository;
import superfon.model.Customer;
import superfon.repository.connection.DatabaseConnector;
import superfon.util.PasswordGenerator;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Repository
public class SqlQuery {

    private PreparedStatement pst = null;

    private Connection connection = DatabaseConnector.getConnection();

    public List<Customer> getAllCustomers() {
        List<Customer> customers = new ArrayList<>();
        Customer customer = null;
        try {
            String sql = "SELECT * FROM Customer";
            if (connection != null) {
                pst = connection.prepareStatement(sql);
                ResultSet rs = pst.executeQuery();
                while (rs.next()) {
                    customer = new Customer();
                    customer.setFullName(rs.getString("fullname"));
                    customer.setPhoneNumber(rs.getString("phone_number"));
                    customer.setGender(rs.getString("gender"));
                    customer.setBirthDate(rs.getString("birth_date"));
                    customer.setMaritalStatus(rs.getString("marital_status"));
                    customer.setTimeStamp(rs.getString("timestamp"));
                    customer.setSerialNumber(rs.getString("serialNumber"));
                    customer.setQrCodeContent(rs.getString("qr_code_content"));
                    customer.setPassword(rs.getString("password"));
                    customer.setStatus(rs.getInt("status"));
                    customers.add(customer);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return customers;
    }

    public Customer getCustomerBySerialNumber(String serialNumber) {

        Customer customer = null;
        try {
            String sql = "SELECT * FROM Customer WHERE Customer.serialNumber=?";
            if (connection != null) {
                pst = connection.prepareStatement(sql);
                pst.setString(1, serialNumber);
                ResultSet rs = pst.executeQuery();
                boolean hasAny = true;
                while (rs.next() && hasAny) {
                    customer = new Customer();
                    customer.setFullName(rs.getString("fullname"));
                    customer.setPhoneNumber(rs.getString("phone_number"));
                    customer.setGender(rs.getString("gender"));
                    customer.setBirthDate(rs.getString("birth_date"));
                    customer.setMaritalStatus(rs.getString("marital_status"));
                    customer.setTimeStamp(rs.getString("timestamp"));
                    customer.setSerialNumber(rs.getString("serialNumber"));
                    customer.setQrCodeContent(rs.getString("qr_code_content"));
                    customer.setPassword(rs.getString("password"));
                    customer.setStatus(rs.getInt("status"));
                    hasAny = false;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return customer;
    }

    public Customer getCustomerByQrCodeContent(String qrCode) {

        Customer customer = null;
        try {
            String sql = "SELECT * FROM Customer WHERE Customer.qr_code_content=?";
            if (connection != null) {
                pst = connection.prepareStatement(sql);
                pst.setString(1, qrCode);
                ResultSet rs = pst.executeQuery();
                boolean hasAny = true;
                while (rs.next() && hasAny) {
                    customer = new Customer();
                    customer.setFullName(rs.getString("fullname"));
                    customer.setPhoneNumber(rs.getString("phone_number"));
                    customer.setGender(rs.getString("gender"));
                    customer.setBirthDate(rs.getString("birth_date"));
                    customer.setMaritalStatus(rs.getString("marital_status"));
                    customer.setTimeStamp(rs.getString("timestamp"));
                    customer.setSerialNumber(rs.getString("serialNumber"));
                    customer.setQrCodeContent(rs.getString("qr_code_content"));
                    customer.setPassword(rs.getString("password"));
                    customer.setStatus(rs.getInt("status"));
                    hasAny = false;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return customer;
    }

    public Customer getCustomerByPassword(String password) {

        Customer customer = null;
        try {
            String sql = "SELECT * FROM Customer WHERE Customer.password=?";
            if (connection != null) {
                pst = connection.prepareStatement(sql);
                pst.setString(1, password);
                ResultSet rs = pst.executeQuery();
                boolean hasAny = true;
                while (rs.next() && hasAny) {
                    customer = new Customer();
                    customer.setFullName(rs.getString("fullname"));
                    customer.setPhoneNumber(rs.getString("phone_number"));
                    customer.setGender(rs.getString("gender"));
                    customer.setBirthDate(rs.getString("birth_date"));
                    customer.setMaritalStatus(rs.getString("marital_status"));
                    customer.setTimeStamp(rs.getString("timestamp"));
                    customer.setSerialNumber(rs.getString("serialNumber"));
                    customer.setQrCodeContent(rs.getString("qr_code_content"));
                    customer.setPassword(rs.getString("password"));
                    customer.setStatus(rs.getInt("status"));
                    hasAny = false;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return customer;
    }

    public Customer save(Customer customer) {
        return null;
    }

    public boolean saveAll(List<Customer> customers) {
        boolean affected = true;
        try {
            String sql =
                    "INSERT INTO Customer" +
                            "(fullname, phone_number, gender, birth_date, marital_status, timestamp, serialNumber, " +
                            "qr_code_content, password, status)" +
                            " VALUES(?,?,?,?,?,?,?,?,?,?)";
            if (connection != null) {
                try {
                    connection.setAutoCommit(false);
                    pst = connection.prepareStatement(sql);
                    customers.forEach(customer -> {
                        try {
                            pst.setString(1, customer.getFullName());
                            pst.setString(2, customer.getPhoneNumber());
                            pst.setString(3, customer.getGender());
                            pst.setString(4, customer.getBirthDate());
                            pst.setString(5, customer.getMaritalStatus());
                            pst.setString(6, customer.getTimeStamp());
                            pst.setString(7, customer.getSerialNumber());
                            pst.setString(8, customer.getQrCodeContent());
                            pst.setString(9, customer.getPassword());
                            pst.setInt(10, customer.getStatus());
                            pst.addBatch();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    });
                    int[] affectedRecords = pst.executeBatch();
                    connection.commit();
                    System.out.println(Arrays.toString(affectedRecords));
                    for (int i = 0; i < affectedRecords.length; i++) {
                        if (affectedRecords[i] == 0) {
                            affected = false;
                            break;
                        }
                    }
                } catch (Exception e) {
                    connection.rollback();
                    e.printStackTrace();
                } finally {
                    if (pst != null) {
                        pst.close();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return affected;
    }

    public Customer updateQrContent(String serialNumber, String content) {
        Customer customer = null;
        try {
            String sql =
                    "UPDATE Customer SET qr_code_content=? WHERE serialNumber=?";
            if (connection != null) {
                try {
                    pst = connection.prepareStatement(sql);
                    pst.setString(1, content);
                    pst.setString(2, serialNumber);
                    int affected = pst.executeUpdate();
                    if (affected > 0) {
                        customer = getCustomerByQrCodeContent(content);
                    }
                    System.out.println(affected);
                } catch (Exception e) {
                    connection.rollback();
                    e.printStackTrace();
                } finally {
                    if (pst != null) {
                        pst.close();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return customer;

    }

    public boolean updateStatus(String serialNumber, Integer status) {
        try {
            String sql =
                    "UPDATE Customer SET status=? WHERE serialNumber=?";
            if (connection != null) {
                try {
                    pst = connection.prepareStatement(sql);
                    pst.setInt(1, status);
                    pst.setString(2, serialNumber);
                    int affected = pst.executeUpdate();
                    return affected > 0;
                } catch (Exception e) {
                    connection.rollback();
                    e.printStackTrace();
                } finally {
                    if (pst != null) {
                        pst.close();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

}
