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
@Table(name = "currencies")
public class Currency {
    @SequenceGenerator(
            name = "currencies_sequence",
            sequenceName = "currencies_sequence",
            allocationSize = 1
    )
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "currencies_sequence"
    )
    private Long id;
    private String cod;
    private String pattern;
    private String currency;
    private String change;
    private Integer active;
    @Column(name = "data_exchange")
    private String dataExchange;
    @Column(name = "currency_series")
    private String currencySeries;
    @Column(name = "currency_from")
    private String currencyFrom;
    private String converter;
    private String description;
    @Column(name = "created_at")
    private Timestamp created_at;
    @Column(name = "updated_at")
    private Timestamp updated_at;

    @ManyToOne
    @JoinColumn(name = "country_id")
    private Country countries;
}
