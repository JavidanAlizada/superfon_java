package superfon;


import superfon.model.Customer;
import superfon.repository.query.SqlQuery;
import superfon.service.CustomerService;
import superfon.service.GoogleSheetService;

public class Main {

    public static void main(String... args) {
        new CustomerService().saveCustomers();
    }
}


//for (List row : values) {
//                // Print columns A and E, which correspond to indices 0 and 4.
//                System.out.println(row.get(3));
//            }