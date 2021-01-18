package superfon;

import superfon.service.CustomerService;

public class Main {

    public static void main(String... args) {
        new CustomerService().saveCustomers();
//        new CustomerService().getAllCustomers();
    }
}
