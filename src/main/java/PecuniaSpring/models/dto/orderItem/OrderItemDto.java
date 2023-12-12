package PecuniaSpring.models.dto.orderItem;

import PecuniaSpring.models.dto.coin.CoinDto;
import PecuniaSpring.models.dto.note.NoteDto;
import PecuniaSpring.models.dto.order.OrderDto;
import PecuniaSpring.models.dto.security.SecurityDto;
import lombok.*;

import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class OrderItemDto {
    private Long id;
    private String pattern;
    private Integer quantity;
    private String unitQuantity;
    private Double finalPrice;
    private String description;
    private Timestamp created_at;
    private Timestamp updated_at;

    private OrderDto orders;
    private NoteDto notes;
    private CoinDto coins;
    private SecurityDto securities;
}
