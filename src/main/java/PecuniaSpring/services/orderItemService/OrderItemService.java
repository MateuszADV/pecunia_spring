package PecuniaSpring.services.orderItemService;

import PecuniaSpring.models.OrderItem;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface OrderItemService {
    List<OrderItem> getAllOrderItem();
    void saveOrderItem(OrderItem orderItem);
    OrderItem saveOrderItemGet(OrderItem orderItem);
    OrderItem getOrderItemFindById(Long id);
    void deleteOrderItemById(Long id);

    List<OrderItem> getOrderItemByNumberOrder(String numberOrder);
    List<OrderItem> getOrderItemByOrderId(Long orderId);

//    String getLastNumberOrder();
//    Boolean checkLastNumberOrder(String lastNumberOrder);
//    String returnFirstNumberOrder();
//    String getNextNumber(String number);
//    Boolean checkYearOrder(String lastNumberOrder);
//    String getNextNumberOrder(String lastNumberOrder);
//
//    String getDateOrder();
}

