package PecuniaSpring.services.orderService;

import PecuniaSpring.models.Order;
import PecuniaSpring.models.repositories.OrderRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

@Service
@NoArgsConstructor
@AllArgsConstructor
public class OrderServiceImpl implements OrderService {

    @Autowired
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

    @Override
    public String getLastNumberOrder() {
        Order order = orderRepository.getLastOrder();
        String lastNumberOrder = order.getNumberOrder();
        return lastNumberOrder;
    }

    @Override
    public Boolean checkLastNumberOrder(String lastNumberOrder) {
        Pattern pattern = Pattern.compile("\\d{4}/\\d{2}/\\d{3,5}/\\d{3,5}");
//        if (pattern.matcher(lastNumberOrder).matches()) {
//            return true;
//        }
//        return false;
        return pattern.matcher(lastNumberOrder).matches();
    }

    @Override
    public String getDateOrder() {
        LocalDate localDate = LocalDate.now();
        Integer year = localDate.getYear();
        Integer month = localDate.getMonthValue();

        String dateOrder = year.toString() + '/' + month.toString();
        return dateOrder;
    }
}
