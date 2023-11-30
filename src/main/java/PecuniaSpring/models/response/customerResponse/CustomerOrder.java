package PecuniaSpring.models.response.customerResponse;

import PecuniaSpring.models.dto.customer.CustomerDto;
import PecuniaSpring.models.dto.order.OrderDto;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class CustomerOrder {
    private CustomerDto customer;
    private List<OrderDto> orders;
}
