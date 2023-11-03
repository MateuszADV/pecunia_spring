package PecuniaSpring.controllers.viewControllers;

import PecuniaSpring.models.Customer;
import PecuniaSpring.models.dto.customer.CustomerDto;
import PecuniaSpring.models.dto.customer.CustomerFormDto;
import PecuniaSpring.services.customer.CustomerServiceImpl;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import utils.JsonUtils;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Controller
@AllArgsConstructor
public class CustomerController {

    private CustomerServiceImpl customerService;

    @GetMapping("/customer")
    public String getIndex(ModelMap modelMap) {
        List<Customer> customers = customerService.getAllCustomer();
        List<CustomerDto> customerDtos = new ArrayList<>();
        for (Customer customer : customers) {
            customerDtos.add(new ModelMapper().map(customer, CustomerDto.class));
        }

        modelMap.addAttribute("customerDtos", customerDtos);

        System.out.println(JsonUtils.gsonPretty(customerDtos));

        UUID uuid = UUID.randomUUID();
        System.out.println(uuid);
        return "customer/index";
    }

    @GetMapping("/customer/new")
    public String getNew(ModelMap modelMap) {
        CustomerFormDto customerFormDto = new CustomerFormDto();
        customerFormDto.setUniqueId(UUID.randomUUID().toString());
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
}
