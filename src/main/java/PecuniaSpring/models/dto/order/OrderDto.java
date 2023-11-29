package PecuniaSpring.models.dto.order;

import PecuniaSpring.models.dto.customer.CustomerDto;
import PecuniaSpring.models.dto.shippingType.ShippingTypeDto;
import PecuniaSpring.models.dto.statusOrder.StatusOrderDto;
import lombok.*;

import java.sql.Date;
import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class OrderDto {

    private Long id;
    private String numberOrder;
    private Date dateOrder;
    private Date dateSendOrder;
    private String trackingNumber;
    private Double shippingCost;
    private String description;
    private Timestamp created_at;
    private Timestamp updated_at;

    private StatusOrderDto statusOrders;
    private ShippingTypeDto shippingTypes;
    private CustomerDto customers;
}
