package PecuniaSpring.models;

import lombok.*;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Entity
@Table(name = "orders")
public class Order {
    @SequenceGenerator(
            name = "orders_sequence",
            sequenceName = "orders_sequence",
            allocationSize = 1
    )
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "orders_sequence"
    )
    private Long id;
    @Column(name = "number_order")
    private String numberOrder;
    @Column(name = "date_order")
    private Date dateOrder;
    @Column(name = "date_send_order")
    private Date dateSendOrder;
    @Column(name = "status_order")
    private String statusOrder;
    @Column(name = "tracking_number")
    private String trackingNumber;
    private String shippingType;   //Oobna klasa z rodzajami wysyłki;
    @Column(name = "shipping_cost")
    private Double shippingCost;
    private String description;
    @Column(name = "created_at")
    private Timestamp created_at;
    @Column(name = "updated_at")
    private Timestamp updated_at;

    @ManyToOne
    @JoinColumn(name = "shipping_type_id")
    private ShippingType shippingTypes;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customers;

}
