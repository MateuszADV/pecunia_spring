package PecuniaSpring.models.dto.shippingType;

import lombok.*;

import javax.validation.constraints.NotEmpty;
import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class ShippingTypeDtoForm {

    private Long id;
    private String shippingTypeEn;
    @NotEmpty(message = "Rodzaj przesyłki musi być podany")
    private String shippingTypePl;
    private Double shippingCost;
    private String description;
    private Timestamp created_at;
    private Timestamp updated_at;
}
