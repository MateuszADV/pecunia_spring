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
@Table(name = "continents")
public class Continent {

    @SequenceGenerator(
            name = "continents_sequence",
            sequenceName = "continents_sequence",
            allocationSize = 1
    )
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "continents_sequence"
    )
    private Long id;
    @Column(name = "continent_en")
    private String continentEn;
    @Column(name = "continent_pl")
    private String continentPl;
    @Column(name = "continent_code")
    private String continentCode;
    @Column(name = "created_at")
    private Timestamp created_at;
    @Column(name = "updated_at")
    private Timestamp updated_at;
}
