package PecuniaSpring.numberOrder;

import PecuniaSpring.services.orderService.OrderServiceImpl;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class DateOrder {

    @Autowired
    OrderServiceImpl orderService = new OrderServiceImpl();

    @Test
    public void should_number_order() {
        //given
        String lastNumberOrder = "2019/12/013/013";

        //when
        String[] elementsOrder = lastNumberOrder.split("/");

        //then

        Assert.assertEquals(elementsOrder.length, 4);
        Assert.assertNotEquals(elementsOrder.length, 5);


//        System.out.println(elementsOrder.length);
//        for (String s : elementsOrder) {
//            System.out.println(s);
//        }
    }

    @Test
    public void should_date_order() {
        System.out.println(orderService.getDateOrder());
        assertNotNull(orderService.getDateOrder());
    }

    @Test
    public void should_last_number_Order() {
        String lastNumberOrder = orderService.getLastNumberOrder();

    }

}
