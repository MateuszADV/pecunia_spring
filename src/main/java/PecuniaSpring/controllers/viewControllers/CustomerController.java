package PecuniaSpring.controllers.viewControllers;

import PecuniaSpring.models.Customer;
import PecuniaSpring.models.dto.customer.CustomerDto;
import PecuniaSpring.models.dto.customer.CustomerFormDto;
import PecuniaSpring.services.customerService.CustomerServiceImpl;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import utils.JsonUtils;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Controller
@AllArgsConstructor
public class CustomerController {

    private CustomerServiceImpl customerService;
    private Optional<Customer> customerTemp;

    @GetMapping("/customer")
    public String getIndex(ModelMap modelMap) {
        List<Customer> customers = customerService.getAllCustomerOrderByID();
        List<CustomerDto> customerDtos = new ArrayList<>();
        for (Customer customer : customers) {
            customerDtos.add(new ModelMapper().map(customer, CustomerDto.class));
        }

        modelMap.addAttribute("customerDtos", customerDtos);

        System.out.println(JsonUtils.gsonPretty(customerDtos));
        return "customer/index";
    }

    @GetMapping("/customer/new")
    public String getNew(ModelMap modelMap) {
        CustomerFormDto customerFormDto = new CustomerFormDto();
        customerFormDto.setUniqueId(UUID.randomUUID().toString());
        customerFormDto.setActive(true);
        modelMap.addAttribute("customerForm", customerFormDto);
        return "customer/new";
    }

    @PostMapping("/customer/new")
    public String posNew(@ModelAttribute("customerForm")@Valid CustomerFormDto customerFormDto, BindingResult result, ModelMap modelMap) {
        System.out.println(JsonUtils.gsonPretty(customerFormDto));

        if (result.hasErrors()) {
            return "customer/new";
        }
        Customer customer = new ModelMapper().map(customerFormDto, Customer.class);
        customerService.saveCustomer(customer);
        return "redirect:/customer";
    }

    @GetMapping("/customer/edit/{customerId}")
    public String getEdit(@PathVariable Long customerId, ModelMap modelMap) {
        customerTemp = Optional.ofNullable(customerService.getCustomerById(customerId));
        CustomerFormDto customerFormDto = new ModelMapper().map(customerTemp, CustomerFormDto.class);
        modelMap.addAttribute("customerForm", customerFormDto);
        return "customer/edit";
    }

    @PostMapping("/customer/edit")
    public String postEdit(@ModelAttribute("customerForm")@Valid CustomerFormDto customerFormDto, BindingResult result, ModelMap modelMap) {
        System.out.println(JsonUtils.gsonPretty(customerFormDto));
        if (result.hasErrors()) {
            return "customer/edit";
        }
        Customer customer= new ModelMapper().map(customerFormDto, Customer.class);
//        bought.setId(boughtTemp.get().getId());
        try {
            customerService.updateCustomer(customer);
        }catch (Exception e) {
            modelMap.addAttribute("error", e.getMessage());
            return "error";
        }

        return "redirect:/customer";
    }

    @GetMapping("/customer/delete/{id}")
    public String getDelete(@PathVariable Long id, ModelMap modelMap) {
        try {
            customerService.deleteCustomerById(id);
            System.out.println("***************************************************");
            System.out.println("****KLIENT ZOZTAl USUNIETY*******************");
            System.out.println("***************************************************");
            return "redirect:/customer";
        }catch (Exception e) {
            modelMap.addAttribute("error", e.getMessage());
            return "error";
        }
    }
}
