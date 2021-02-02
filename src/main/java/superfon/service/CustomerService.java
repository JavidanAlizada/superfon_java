package superfon.service;

import org.springframework.stereotype.Service;
import superfon.model.Customer;
import superfon.repository.query.SqlQuery;
import superfon.util.PasswordGenerator;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerService {

    SqlQuery query = new SqlQuery();

    private PasswordGenerator generator;

    public List<Customer> saveCustomers() {
        GoogleSheetService service = new GoogleSheetService();
        Customer customer = null;
        List<Customer> list = new ArrayList<>();
        List<List<Object>> liste = service.getSheetData();
        for (List<Object> data : liste) {
            if ((liste.indexOf(data) + 1) > getAllCustomers().size()) {
                customer = new Customer();
                String content = data.get(6).toString() + "_" + data.get(2).toString() + "_" + data.get(1).toString();
                generator = new PasswordGenerator();
                customer.setFullName(data.get(1).toString());
                customer.setPhoneNumber(data.get(2).toString());
                customer.setGender(data.get(3).toString());
                customer.setBirthDate(data.get(4).toString());
                customer.setMaritalStatus(data.get(5).toString());
                customer.setTimeStamp(data.get(0).toString());
                customer.setSerialNumber(data.get(6).toString());
                customer.setQrCodeContent(content);
                customer.setPassword(generator.getGeneratedPassword());
                customer.setStatus(0);
                list.add(customer);
            }
        }
        boolean saved = query.saveAll(list);
        return saved ? list : null;
    }

    public List<Customer> getAllCustomers() {
        return query.getAllCustomers();
    }

    public Customer getBySerialNumber(String serialNumber) {
        Customer customer = null;
        if (serialNumber != null && !serialNumber.isEmpty()) {
            customer = query.getCustomerBySerialNumber(serialNumber);
        }
        return customer;
    }

    public Customer getByPassword(String password) {
        Customer customer = null;
        if (password != null && !password.isEmpty()) {
            customer = query.getCustomerByPassword(password);
        }
        return customer;
    }

    public Customer getByQrCodeContent(String qrCode) {
        Customer customer = null;
        if (qrCode != null && !qrCode.isEmpty()) {
            customer = query.getCustomerByQrCodeContent(qrCode);
        }
        return customer;
    }

    public Customer updateQrCodeContent(String serialNumber, String qrCode) {
        Customer customer = null;
        if (qrCode != null && !qrCode.isEmpty()) {
            customer = query.updateQrContent(serialNumber, qrCode);
        }
        return customer;
    }

    public boolean updateStatus(String serialNumber, Integer status) {
        return query.updateStatus(serialNumber, status);
    }


}
