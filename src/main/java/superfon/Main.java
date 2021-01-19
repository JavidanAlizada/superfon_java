package superfon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import superfon.service.CustomerService;

@SpringBootApplication
public class Main {

    public static void main(String... args) {
        SpringApplication.run(Main.class, args);
//        new CustomerService().saveCustomers();
//        System.out.println(new CustomerService().getAllCustomers());
//        System.out.println(new CustomerService().getBySerialNumber("AZE05677777"));
    }
}
