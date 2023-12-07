package PecuniaSpring.services.orderService;

import PecuniaSpring.models.Order;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface OrderService {
    List<Order> getAllOrder();
    void saveOrder(Order order);
    Order saveOrderGet(Order order);
    Order getOrderFindById(Long id);
    void deleteOrderById(Long id);

    List<Order> getOrderBycustomer(String customerUUID);

    String getLastNumberOrder();
    Boolean checkLastNumberOrder(String lastNumberOrder);

    String getDateOrder();
}

