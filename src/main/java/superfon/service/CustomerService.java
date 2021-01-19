package superfon.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import superfon.model.Customer;
import superfon.repository.query.SqlQuery;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerService {

    @Autowired
    private SqlQuery query;


    public List<Customer> saveCustomers() {
        GoogleSheetService service = new GoogleSheetService();
        Customer customer = null;
        List<Customer> list = new ArrayList<>();
        List<List<Object>> liste = service.getSheetData();
        liste.forEach(System.out::println);
        for (List<Object> data : liste) {
            if ((liste.indexOf(data) + 1) > getAllCustomers().size()) {
                customer = new Customer();
                customer.setFullName(data.get(1).toString());
                customer.setPhoneNumber(data.get(2).toString());
                customer.setGender(data.get(3).toString());
                customer.setBirthDate(data.get(4).toString());
                customer.setMaritalStatus(data.get(5).toString());
                customer.setTimeStamp(data.get(0).toString());
                customer.setSerialNumber(data.get(6).toString());
                list.add(customer);
            }
        }
        boolean saved = query.saveAll(list);
        return saved ? list : null;
    }

    public List<Customer> getAllCustomers() {
        return query.getAllCustomers();
    }

    //serialnumber
    public Customer getBySerialNumber(String serialNumber) {
        Customer customer = null;
        if (serialNumber != null && !serialNumber.isEmpty()) {
            customer = query.getCustomerBySerialNumber(serialNumber);
        }
        return customer;
    }

}
