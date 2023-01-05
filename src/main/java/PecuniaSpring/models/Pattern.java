package PecuniaSpring.models;

import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Entity
@Table(name = "patterns")
public class Pattern {

    @SequenceGenerator(
            name = "patterns_sequence",
            sequenceName = "patterns_sequence",
            allocationSize = 1
    )
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "patterns_sequence"
    )
    private Long id;
    private String pattern;
    private String name;
    private String description;
    @Column(name = "created_at")
    private Timestamp created_at;
    @Column(name = "updated_at")
    private Timestamp updated_at;

    @OneToMany(mappedBy = "patterns", cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    private List<Currency> currencies;
}
