package superfon.service;

import superfon.model.Customer;
import superfon.repository.query.SqlQuery;

import java.util.ArrayList;
import java.util.List;

public class CustomerService {
    private final SqlQuery query;

    public CustomerService() {
        query = new SqlQuery();
    }

    public List<Customer> saveCustomers() {
        GoogleSheetService service = new GoogleSheetService();
        Customer customer = null;
        List<Customer> list = new ArrayList<>();
        for (List<Object> data : service.getSheetData()) {
            customer = new Customer();
            customer.setFullName(data.get(1).toString());
            customer.setPhoneNumber(data.get(2).toString());
            customer.setGender(data.get(3).toString());
            customer.setBirthDate(data.get(4).toString());
            customer.setMaritalStatus(data.get(5).toString());
            customer.setTimeStamp(data.get(0).toString());
            list.add(customer);
        }
        boolean saved = query.saveAll(list);
        return saved ? list : null;
    }

}
