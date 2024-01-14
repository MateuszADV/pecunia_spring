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

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.BDDMockito.given;


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
//        Assert.assertThat(orders, Matchers.hasSize(2));
    }

    @Test
    public void should_check_numberOrder() {
        //when
        String lastNumberOrder = lastNumberOrder(prpareMockData());

        //then
        Assert.assertTrue("Błędny numer zamówienia",orderService.checkLastNumberOrder(lastNumberOrder));
    }

    private String lastNumberOrder(List<Order> orders) {

        return orders.get(orders.size()-1).getNumberOrder();
    }

    private List<Order> prpareMockData() {
        List<Order> orders = new ArrayList<>();
        Order order = new Order();
        order.setId(1l);
        order.setNumberOrder("2023/12/0012/0010");

        Order order1 = new Order();
        order1.setId(1l);
        order1.setNumberOrder("2023/12/0013/0011");

        orders.add(order);
        orders.add(order1);

        return orders;
    }

    @Test
    public void should_first_number_order() {
        LocalDate localDate = LocalDate.now();
        Integer year = localDate.getYear();
        Integer month = localDate.getMonthValue();

        String dateOrder = year.toString() + '/' + month.toString();
        Assert.assertEquals(orderService.returnFirstNumberOrder(), dateOrder + "/0001/0001");
    }

    @Test
    public void should_get_next_number() {
        System.out.println(orderService.getNextNumber("0000"));
        Assert.assertEquals(orderService.getNextNumber("0000"), "0001");

        System.out.println(orderService.getNextNumber("0010"));
        Assert.assertEquals(orderService.getNextNumber("0010"), "0011");

        System.out.println(orderService.getNextNumber("0210"));
        Assert.assertEquals(orderService.getNextNumber("0210"), "0211");

        System.out.println(orderService.getNextNumber("3210"));
        Assert.assertEquals(orderService.getNextNumber("3210"), "3211");
    }

    @Test
    public void should_get_next_number_order() {
        Assert.assertEquals(orderService.getNextNumberOrder("2023/12/0015/0009"), "2023/12/0016/0010");
        System.out.println("2023/12/0015/0009 > " + orderService.getNextNumberOrder("2023/12/0015/0009"));
        Assert.assertEquals(orderService.getNextNumberOrder("2022/10/0018/0015"), "2023/12/0019/0001");
        System.out.println("2022/10/0018/0015 > " + orderService.getNextNumberOrder("2022/10/0018/0015"));

    }
}
