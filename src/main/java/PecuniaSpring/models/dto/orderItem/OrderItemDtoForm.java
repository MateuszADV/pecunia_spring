package PecuniaSpring.models.dto.orderItem;

import PecuniaSpring.models.dto.coin.CoinDto;
import PecuniaSpring.models.dto.note.NoteDto;
import PecuniaSpring.models.dto.order.OrderDto;
import PecuniaSpring.models.dto.security.SecurityDto;
import lombok.*;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class OrderItemDtoForm {
    private Long id;
    private String pattern;
    private Integer quantity;
    private String unitQuantity;
    @Digits(integer=6, fraction = 2, message = "Podaj poprawną cenę (1.23)")
    @DecimalMin(value = "0.00", message = "Wartość nie może być ujemna")
    @NotNull(message = "Wartość nie może być pusta")
    private Double finalPrice;
    private String description;
    private Timestamp created_at;
    private Timestamp updated_at;

    private OrderDto orders;
    private NoteDto notes;
    private CoinDto coins;
    private SecurityDto securities;
}
