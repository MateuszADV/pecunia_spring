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
@Table(name = "countries")
public class Country {
    @SequenceGenerator(
            name = "countries_sequence",
            sequenceName = "countries_sequence",
            allocationSize = 1
    )
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "countries_sequence"
    )
    private Long id;
    private String continent;
    @Column(name = "country_en")
    private String countryEn;
    @Column(name = "country_pl")
    private String country_pl;
    @Column(name = "capital_city")
    private String capital_city;
    @Column(name = "alfa_2")
    private String alfa_2;
    @Column(name = "alfa_3")
    private String alfa_3;
    @Column(name = "numeric_code")
    private String numeric_code;
    @Column(name = "iso_code")
    private String iso_code;
    @Column(name = "created_at")
    private Timestamp created_at;
    @Column(name = "updated_at")
    private Timestamp updated_at;
    private Boolean exists;
    private String description;
}
