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
@Table(name = "boughts")
public class Bought {
    @SequenceGenerator(
            name = "boughts_sequence",
            sequenceName = "boughts_sequence",
            allocationSize = 1
    )
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "boughts_sequence"
    )
    private Long id;
    private String name;
    @Column(name = "full_name")
    private String fullName;
    private String description;
    @Column(name = "created_at")
    private Timestamp created_at;
    @Column(name = "updated_at")
    private Timestamp updated_at;

//    @OneToMany(mappedBy = "boughts", cascade = {CascadeType.MERGE, CascadeType.PERSIST})
//    private List<Note> notes;
}
