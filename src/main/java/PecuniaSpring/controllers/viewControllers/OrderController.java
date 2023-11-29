package PecuniaSpring.controllers.viewControllers;

import PecuniaSpring.models.Order;
import PecuniaSpring.models.dto.order.OrderDto;
import PecuniaSpring.services.orderService.OrderServiceImpl;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@AllArgsConstructor
public class OrderController {

    private OrderServiceImpl orderService;

    @GetMapping("/order")
    public String getIndex(ModelMap modelMap) {
        List<Order> orders = orderService.getAllOrder();
        List<OrderDto> orderDtos = new ArrayList<>();
        for (Order order : orders) {
            orderDtos.add(new ModelMapper().map(order, OrderDto.class));
        }
        modelMap.addAttribute("orderDtos", orderDtos);
        return "order/index";
    }
}
