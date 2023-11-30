package PecuniaSpring.controllers.viewControllers;

import PecuniaSpring.models.Customer;
import PecuniaSpring.models.Order;
import PecuniaSpring.models.ShippingType;
import PecuniaSpring.models.StatusOrder;
import PecuniaSpring.models.dto.customer.CustomerDto;
import PecuniaSpring.models.dto.customer.CustomerGetDto;
import PecuniaSpring.models.dto.order.OrderDto;
import PecuniaSpring.models.dto.order.OrderDtoForm;
import PecuniaSpring.models.dto.shippingType.ShippingTypeDtoSelect;
import PecuniaSpring.models.dto.statusOrder.StatusOrderDtoSelect;
import PecuniaSpring.models.response.customerResponse.CustomerOrder;
import PecuniaSpring.services.customerService.CustomerServiceImpl;
import PecuniaSpring.services.orderService.OrderServiceImpl;
import PecuniaSpring.services.shippingType.ShippingTypeServiceImpl;
import PecuniaSpring.services.statusOrderService.StatusOrderServiceImpl;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import utils.JsonUtils;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Controller
@AllArgsConstructor
public class OrderController {

    private OrderServiceImpl orderService;
    private CustomerServiceImpl customerService;
    private ShippingTypeServiceImpl shippingTypeService;
    private StatusOrderServiceImpl statusOrderService;

    @GetMapping("/order/{customerUUID}")
    public String getIndex(@PathVariable String customerUUID, ModelMap modelMap) {
        CustomerOrder customerOrder = new CustomerOrder();
        System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
        System.out.println(customerUUID);
        Customer customer = customerService.getCustomerByUUID(customerUUID);
        customerOrder.setCustomer(new ModelMapper().map(customer, CustomerDto.class));
        List<Order> orders = new ArrayList<>();
        try {
            orders = orderService.getOrderBycustomer(customerUUID);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");

        List<OrderDto> orderDtos = new ArrayList<>();
        for (Order order : orders) {
            orderDtos.add(new ModelMapper().map(order, OrderDto.class));
        }
        customerOrder.setOrders(orderDtos);

        System.out.println(JsonUtils.gsonPretty(customerOrder));
        modelMap.addAttribute("customerOrder", customerOrder);
        return "order/index";
    }

    @GetMapping("/order/new")
    public String getNew(@RequestParam("customerId") Long customerId, ModelMap modelMap) {
        System.out.println("===================Country ID===================");
        Customer customer = customerService.getCustomerById(customerId);
        CustomerGetDto customerGetDto = new ModelMapper().map(customer, CustomerGetDto.class);

        List<ShippingType> shippingTypes = shippingTypeService.getAllShippingType();
        List<ShippingTypeDtoSelect> shippingTypeDtoSelects = new ArrayList<>();
        for (ShippingType shippingType : shippingTypes) {
            shippingTypeDtoSelects.add(new ModelMapper().map(shippingType, ShippingTypeDtoSelect.class));
        }

        List<StatusOrder> statusOrders = statusOrderService.getAllStatusOrder();
        List<StatusOrderDtoSelect> statusOrderDtoSelects = new ArrayList<>();
        for (StatusOrder statusOrder : statusOrders) {
            statusOrderDtoSelects.add(new ModelMapper().map(statusOrder, StatusOrderDtoSelect.class));
        }

        modelMap.addAttribute("shippmentSelect", shippingTypeDtoSelects);
        modelMap.addAttribute("statusOrderSelect", statusOrderDtoSelects);

        OrderDtoForm orderDtoForm = new OrderDtoForm();
        orderDtoForm.setDateOrder(Date.valueOf(LocalDate.now()));
        orderDtoForm.setCustomers(customerGetDto);
        modelMap.addAttribute("orderForm", orderDtoForm);

        System.out.println("================================================");
        return "order/new";
    }
}
