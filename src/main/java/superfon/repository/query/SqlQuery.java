package superfon.repository.query;

import superfon.model.Customer;
import superfon.repository.connection.DatabaseConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

public class SqlQuery {

    private PreparedStatement pst = null;

    private Connection connection = DatabaseConnector.getConnection();

    public List<Customer> getAllCustomers() {
        return null;
    }

    public Customer getCustomerByPhoneNumber(String phoneNumber) {
        return null;
    }

    public Customer save(Customer customer) {
        return null;
    }

    public boolean saveAll(List<Customer> customers) {
        boolean affected = true;
        try {
            String sql =
                    "INSERT INTO Customer" +
                            "(fullname, phone_number, gender, birth_date, marital_status, timestamp)" +
                            " VALUES(?,?,?,?,?,?)";
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
