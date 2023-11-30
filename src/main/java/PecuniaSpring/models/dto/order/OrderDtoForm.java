package PecuniaSpring.models.dto.order;

import PecuniaSpring.models.dto.customer.CustomerGetDto;
import PecuniaSpring.models.dto.shippingType.ShippingTypeDtoSelect;
import PecuniaSpring.models.dto.statusOrder.StatusOrderDtoSelect;
import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.sql.Date;
import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class OrderDtoForm {

    private Long id;
    private String numberOrder;
    private Date dateOrder;
    private Date dateSendOrder;
    private String trackingNumber;
    private Double shippingCost;
    private String description;
    private Timestamp created_at;
    private Timestamp updated_at;

    @NotNull
    @Valid
    private StatusOrderDtoSelect statusOrders;
    @NotNull
    @Valid
    private ShippingTypeDtoSelect shippingTypes;
    private CustomerGetDto customers;
}
