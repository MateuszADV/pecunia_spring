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
@Table(name = "status_orders")
public class StatusOrder {
    @SequenceGenerator(
            name = "status_orders_sequence",
            sequenceName = "status_orders_sequence",
            allocationSize = 1
    )
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "status_orders_sequence"
    )
    private Long id;
    @Column(name = "status_en")
    private String statusEn;
    @Column(name = "status_pl")
    private String statusPl;
    private String description;
    @Column(name = "created_at")
    private Timestamp created_at;
    @Column(name = "updated_at")
    private Timestamp updated_at;
}
