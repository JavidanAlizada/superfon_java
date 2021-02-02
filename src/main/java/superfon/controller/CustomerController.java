package superfon.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import superfon.model.Customer;
import superfon.service.CustomerService;
import superfon.util.ResponseFormatter;

import javax.servlet.http.HttpServletRequest;
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
    public Map<String, Object> getCustomerDataBySerialNumber(@RequestParam("serialNumber") String serialNumber) {
        ResponseFormatter<Customer> formatter = new ResponseFormatter<>();
        Map<String, Object> response = new HashMap<>();
        Customer customer = service.getBySerialNumber(serialNumber);
        if (customer != null)
            return formatter.formatResponse(true, customer, null);
        else
            return formatter.formatResponse(false, null, "Customer is not found");
    }

    @RequestMapping(value = "/byPassword", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @GetMapping
    public Map<String, Object> getCustomerDataByPassword(@RequestParam("password") String serialNumber) {
        ResponseFormatter<Customer> formatter = new ResponseFormatter<>();
        Customer customer = service.getByPassword(serialNumber);
        if (customer != null)
            return formatter.formatResponse(true, customer, null);
        else
            return formatter.formatResponse(false, null, "Customer is not found");
    }

    @RequestMapping(value = "/byQrCodeContent", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @GetMapping
    public Map<String, Object> getCustomerDataByQrCodeContent(@RequestParam("qrCodeContent") String qrCodeContent) {
        ResponseFormatter<Customer> formatter = new ResponseFormatter<>();
        Map<String, Object> response = new HashMap<>();
        Customer customer = service.getByQrCodeContent(qrCodeContent);
        if (customer != null)
            return formatter.formatResponse(true, customer, null);
        else
            return formatter.formatResponse(false, null, "Customer is not found");
    }

    @RequestMapping(value = "/updateQrCode/{serialNumber}/", method = RequestMethod.PATCH, produces = MediaType.APPLICATION_JSON_VALUE)
    @PatchMapping
    public Map<String, Object> updateQrCodeContent(HttpServletRequest request,
                                                   @PathVariable("serialNumber") String serialNumber,
                                                   @RequestBody String content) {
        System.out.println(content);
        System.out.println(request.getHeader("Content-Type"));
        ResponseFormatter<Customer> formatter = new ResponseFormatter<>();
        Customer customer = service.updateQrCodeContent(serialNumber, content);
        if (customer != null)
            return formatter.formatResponse(true, customer, null);
        else
            return formatter.formatResponse(false, null, "Customer is not found");
    }

    @RequestMapping(value = "/status/{serialNumber}/", method = RequestMethod.PATCH, produces = MediaType.APPLICATION_JSON_VALUE)
    @PatchMapping
    public Map<String, Object> updateStatus(HttpServletRequest request,
                                            @PathVariable("serialNumber") String serialNumber,
                                            @RequestBody Integer status) {
        ResponseFormatter<Map<String, Boolean>> formatter = new ResponseFormatter<>();
        Map<String, Boolean> statusObj = new HashMap<>();
        statusObj.put("status", service.updateStatus(serialNumber, status));
        if (statusObj.get("status"))
            return formatter.formatResponse(true, statusObj, null);
        else
            return formatter.formatResponse(false, null, "Status could not be updated");
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
