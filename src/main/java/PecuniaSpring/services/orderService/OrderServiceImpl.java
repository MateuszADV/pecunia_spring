package PecuniaSpring.services.orderService;

import PecuniaSpring.models.Order;
import PecuniaSpring.models.repositories.OrderRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class OrderServiceImpl implements OrderService {

    private OrderRepository orderRepository;

    @Override
    public List<Order> getAllOrder() {
        return this.orderRepository.findAll();
    }

    @Override
    public void saveOrder(Order order) {
        this.orderRepository.save(order);
    }

    @Override
    public Order saveOrderGet(Order order) {
        return this.orderRepository.save(order);
    }

    @Override
    public Order getOrderFindById(Long id) {
        Optional<Order> optional = orderRepository.findById(id);
        if (optional.isPresent()) {
            return optional.get();
        } else {
            throw new RuntimeException("Order Not Found For Id :: " + id);
        }
    }

    @Override
    public void deleteOrderById(Long id) {
        Optional<Order> optional = orderRepository.findById(id);
        if (optional.isPresent()) {
            orderRepository.deleteById(id);
        } else {
            throw new RuntimeException("Element O podamyn id - " + id + " ,który chcesz usunąć nie istnieje");
        }
    }

    @Override
    public List<Order> getOrderBycustomer(String customerUUID) {
        List<Order> orders = orderRepository.getOrderbyCustomerUUID(customerUUID);
        return orders;
    }
}
