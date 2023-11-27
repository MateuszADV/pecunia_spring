package PecuniaSpring.models;

import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Entity
@Table(name = "shipping_types")
public class ShippingType {
    @SequenceGenerator(
            name = "shipping_types_sequence",
            sequenceName = "shipping_types_sequence",
            allocationSize = 1
    )
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "shipping_types_sequence"
    )
    private Long id;
    @Column(name = "shipping_type_en")
    private String shippingTypesEn;
    @Column(name = "shipping_type_pl")
    private String shippingTypesPl;
    @Column(name = "shipping_cost")
    private Double shippingCost;
    private String description;
    @Column(name = "created_at")
    private Timestamp created_at;
    @Column(name = "updated_at")
    private Timestamp updated_at;
}
