package PecuniaSpring.numberOrder;

import PecuniaSpring.models.Order;
import PecuniaSpring.models.repositories.OrderRepository;
import PecuniaSpring.services.orderService.OrderServiceImpl;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@RunWith(MockitoJUnitRunner.class)
public class OrderMockTest {

    @Mock
    OrderRepository orderRepository;

    @InjectMocks
    OrderServiceImpl orderService;

    //Adnotacja powoduje że ta metoda wywołuje się przed każdym testem
    @Before
    public void init() {
        given(orderRepository.findAll()).willReturn(prpareMockData());
    }


    @Test
    public void should_get_all_orders() {
        //when
        List<Order> orders = orderService.getAllOrder();

        //then
        Assert.assertThat(orders, Matchers.hasSize(2));
    }

    @Test
    public void should_check_numberOrder() {

        String lastNumberOrder = lastNumberOrder(prpareMockData());
        //when

        //then
        Pattern pattern = Pattern.compile("\\d{4}/\\d{2}/\\d{3,5}/\\d{3,5}");
        System.out.println(pattern.toString());
        Assert.assertTrue(orderService.checkLastNumberOrder(lastNumberOrder));
    }

    private String lastNumberOrder(List<Order> orders) {

        return orders.get(orders.size()-1).getNumberOrder();
    }

    private List<Order> prpareMockData() {
        List<Order> orders = new ArrayList<>();
        Order order = new Order();
        order.setId(1l);
        order.setNumberOrder("2023/12/00012/00010");

        Order order1 = new Order();
        order1.setId(1l);
        order1.setNumberOrder("2023/12/00013/00011");

        orders.add(order);
        orders.add(order1);

        return orders;
    }
}
