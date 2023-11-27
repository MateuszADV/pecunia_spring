package PecuniaSpring.models.dto.shippingType;

import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class ShippingTypeDto {

    private Long id;
    private String shippingTypesEn;
    private String shippingTypesPl;
    private Double shippingCost;
    private String description;
    private Timestamp created_at;
    private Timestamp updated_at;
}
