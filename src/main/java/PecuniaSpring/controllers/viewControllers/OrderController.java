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
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import utils.JsonUtils;

import javax.validation.Valid;
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

//        System.out.println(JsonUtils.gsonPretty(customerOrder));
        modelMap.addAttribute("customerOrder", customerOrder);
        return "order/index";
    }

    @GetMapping("/order/new/{customerUUID}")
    public String getNew(@PathVariable("customerUUID") String customerUUID, ModelMap modelMap) {
        System.out.println("===================Order START===================");
        System.out.println(customerUUID);
        Customer customer = customerService.getCustomerByUUID(customerUUID);
        CustomerGetDto customerGetDto = new ModelMapper().map(customer, CustomerGetDto.class);

        orderParameters(modelMap);

        OrderDtoForm orderDtoForm = new OrderDtoForm();
        orderDtoForm.setNumberOrder(orderService.getNextNumberOrder(orderService.getLastNumberOrder()));
        orderDtoForm.setDateOrder(Date.valueOf(LocalDate.now()));
        orderDtoForm.setDateSendOrder(Date.valueOf(LocalDate.now()));
        orderDtoForm.setShippingCost(0.00);
        orderDtoForm.setCustomers(customerGetDto);
        modelMap.addAttribute("orderForm", orderDtoForm);

        System.out.println("================================================");
        return "order/new";
    }

    @PostMapping("/order/new")
    public String postNew(@ModelAttribute("orderForm")@Valid OrderDtoForm orderDtoForm, BindingResult result, ModelMap modelMap) {

        System.out.println(JsonUtils.gsonPretty(orderDtoForm));
        Customer customer = customerService.getCustomerById(orderDtoForm.getCustomers().getId());
        CustomerGetDto customerGetDto = new ModelMapper().map(customer, CustomerGetDto.class);
        orderDtoForm.setCustomers(customerGetDto);

        System.out.println("=============================");
        System.out.println(orderDtoForm.getDateSendOrder());
        System.out.println("=============================");

        System.out.println(result.toString());

        if (result.hasErrors()) {
            orderParameters(modelMap);
            return "order/new";
        }
        Order order = new ModelMapper().map(orderDtoForm, Order.class);
        orderService.saveOrder(order);
        return "redirect:/order/" + customer.getUniqueId();
//        return getIndex(customer.getUniqueId(), modelMap);
    }

    @GetMapping("/order/edit/{orderId}")
    public String getEdit(@PathVariable Long orderId, ModelMap modelMap) {
        Order order = orderService.getOrderFindById(orderId);
        OrderDtoForm orderDtoForm = new ModelMapper().map(order, OrderDtoForm.class);
        orderParameters(modelMap);
        modelMap.addAttribute("orderForm", orderDtoForm);
        return "order/edit";
    }

    @PostMapping("/order/edit")
    public String postEdit(@ModelAttribute("orderForm")@Valid OrderDtoForm orderDtoForm, BindingResult result, ModelMap modelMap ){
        Customer customer = customerService.getCustomerById(orderDtoForm.getCustomers().getId());
        CustomerGetDto customerGetDto = new ModelMapper().map(customer, CustomerGetDto.class);
        if(result.hasErrors()) {
            return "order/edit";
        }
        Order order = new ModelMapper().map(orderDtoForm, Order.class);
        System.out.println("--------------------------------------");
        System.out.println(JsonUtils.gsonPretty(order));
        System.out.println("---------------------------------------");
        Order orderGetr = orderService.saveOrderGet(order);
        return "redirect:/order/" + customer.getUniqueId();
//        return getIndex(customer.getUniqueId(), modelMap);
    }

    private void orderParameters(ModelMap modelMap) {
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

        modelMap.addAttribute("shippingTypes", shippingTypeDtoSelects);
        modelMap.addAttribute("statusOrders", statusOrderDtoSelects);
        modelMap.addAttribute("standartDate", Date.valueOf(LocalDate.now()));
    }

    @GetMapping("/order/delete/{orderId}")
    public String delete(@PathVariable Long orderId, ModelMap modelMap) {
        Order order = orderService.getOrderFindById(orderId);
        orderService.deleteOrderById(orderId);
        return getIndex(order.getCustomers().getUniqueId(), modelMap);
    }
}
