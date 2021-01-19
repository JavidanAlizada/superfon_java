package superfon.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import superfon.model.Customer;
import superfon.service.CustomerService;
import superfon.util.ResponseFormatter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api/v1/customer")
public class CustomerController {

    @Autowired
    private CustomerService service;

    @RequestMapping(value = "/bySerialNumber", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @GetMapping
    public Map<String, Object> getCustomerData(@RequestParam("serialNumber") String serialNumber) {
        ResponseFormatter<Customer> formatter = new ResponseFormatter<>();
        Map<String, Object> response = new HashMap<>();
        Customer customer = service.getBySerialNumber(serialNumber);
        if (customer != null)
            return formatter.formatResponse(true, customer, null);
        else
            return formatter.formatResponse(false, null, "Customer is not found");
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @GetMapping
    public Map<String, Object> getCustomerData() {
        ResponseFormatter<List<Customer>> formatter = new ResponseFormatter<>();
        List<Customer> customer = service.getAllCustomers();
        if (customer != null && customer.size() != 0)
            return formatter.formatResponse(true, customer, null);
        else
            return formatter.formatResponse(false, null, "CustomerList is not empty");
    }

}
