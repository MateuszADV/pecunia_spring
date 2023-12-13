package PecuniaSpring.controllers.viewControllers;

import PecuniaSpring.models.Order;
import PecuniaSpring.models.OrderItem;
import PecuniaSpring.models.dto.order.OrderDto;
import PecuniaSpring.models.dto.orderItem.OrderItemDto;
import PecuniaSpring.models.response.orderResponse.OrderItemResp;
import PecuniaSpring.models.sqlClass.GetCoinsByStatus;
import PecuniaSpring.models.sqlClass.GetNotesByStatus;
import PecuniaSpring.models.sqlClass.GetSecuritiesByStatus;
import PecuniaSpring.services.coinServices.CoinServiceImpl;
import PecuniaSpring.services.noteServices.NoteServiceImpl;
import PecuniaSpring.services.orderItemService.OrderItemServiceImpl;
import PecuniaSpring.services.orderService.OrderServiceImpl;
import PecuniaSpring.services.securityService.SecurityServiceImpl;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import utils.JsonUtils;

import java.util.ArrayList;
import java.util.List;

@Controller
public class OrderItemController {

    private OrderItemServiceImpl orderItemService;
    private OrderServiceImpl orderService;
    private NoteServiceImpl noteService;
    private CoinServiceImpl coinService;
    private SecurityServiceImpl securityService;

    @Autowired
    public OrderItemController(OrderItemServiceImpl orderItemService, OrderServiceImpl orderService, NoteServiceImpl noteService, CoinServiceImpl coinService, SecurityServiceImpl securityService) {
        this.orderItemService = orderItemService;
        this.orderService = orderService;
        this.noteService = noteService;
        this.coinService = coinService;
        this.securityService = securityService;
    }

    @GetMapping("/orderItem/{orderId}")
    public String getIndex(@PathVariable Long orderId, ModelMap modelMap) {
        System.out.println(orderId);
        OrderItemResp orderItemResp = new OrderItemResp();
        Order order = orderService.getOrderFindById(orderId);
        OrderDto orderDto = new ModelMapper().map(order, OrderDto.class);

        List<OrderItem> orderItems = new ArrayList<>();

        try {
            orderItems = orderItemService.getOrderItemByOrderId(orderId);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        List<OrderItemDto> orderItemDtos = new ArrayList<>();
        for (OrderItem orderItem : orderItems) {
            orderItemDtos.add(new ModelMapper().map(orderItem, OrderItemDto.class));
        }
        orderItemResp.setOrders(orderDto);
        orderItemResp.setOrderItems(orderItemDtos);

        System.out.println("OOOOOOOOOOOOOOOOOOOOOO SART OOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO");
        System.out.println(JsonUtils.gsonPretty(orderItemResp));
        System.out.println("OOOOOOOOOOOOOOOOOOOOOO STOP OOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO");
        modelMap.addAttribute("orderItem", orderItemResp);
        return "orderItem/index";
    }

    @GetMapping("/orderItem/forSell")
    public String getItemForSell(ModelMap modelMap) {
        List<GetNotesByStatus> getNotesByStatusList = noteService.getNoteByStatus("FOR SELL");
        List<GetCoinsByStatus> getCoinsByStatusList = coinService.getCoinsByStatus("FOR SELL");
        List<GetSecuritiesByStatus> getSecuritiesByStatusList = securityService.getSecurityByStatus("FOR SELL");

        System.out.println(JsonUtils.gsonPretty(getCoinsByStatusList));

        modelMap.addAttribute("notesForSell", getNotesByStatusList);
        modelMap.addAttribute("coinsForSell", getCoinsByStatusList);
        modelMap.addAttribute("securitiesForSell", getSecuritiesByStatusList);

        return "orderItem/itemForSell";
    }
}
