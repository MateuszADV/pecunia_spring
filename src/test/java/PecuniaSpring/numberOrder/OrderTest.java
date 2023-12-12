package PecuniaSpring.numberOrder;

import PecuniaSpring.models.Order;
import PecuniaSpring.services.orderService.OrderServiceImpl;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class OrderTest {

    @Test
    public void should_get_all_orders() {
        //given
        OrderServiceImpl orderService = mock(OrderServiceImpl.class);

        //when
        when(orderService.getAllOrder()).thenReturn(prpareMockData());

        //then
        Assert.assertThat(orderService.getAllOrder(), Matchers.hasSize(2));
    }

    //BDD
    @Test
    public void should_get_all_orders_withBDD() {
        //given
        OrderServiceImpl orderService = mock(OrderServiceImpl.class);
        given(orderService.getAllOrder()).willReturn(prpareMockData());
        //when
        List<Order> orders = orderService.getAllOrder();

        //then
        Assert.assertThat(orders, Matchers.hasSize(2));
    }

    @Test
    public void should_add_order() {
        //given
        Order order = new Order();
        order.setNumberOrder("2023/12/00013/00011");
        OrderServiceImpl orderService = mock(OrderServiceImpl.class);
        given(orderService.saveOrderGet(Mockito.any(Order.class))).willReturn(order);

        //when
        Order order1 = orderService.saveOrderGet(new Order());

        //then

        Assert.assertEquals(order1.getNumberOrder(), "2023/12/00013/00011");
    }

    private List<Order>prpareMockData() {
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
