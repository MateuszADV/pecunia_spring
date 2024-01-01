package PecuniaSpring.models.response.orderResponse;

import PecuniaSpring.models.dto.order.OrderDto;
import PecuniaSpring.models.dto.orderItem.OrderItemDto;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class OrderItemResp {
    private OrderDto orders;
    private List<OrderItemDto> orderItems;

}
