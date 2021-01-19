package superfon.repository.query;

import org.springframework.stereotype.Repository;
import superfon.model.Customer;
import superfon.repository.connection.DatabaseConnector;

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
                            "(fullname, phone_number, gender, birth_date, marital_status, timestamp, serialNumber)" +
                            " VALUES(?,?,?,?,?,?,?)";
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

    public Customer update(Customer customer) {
        return null;
    }

}
