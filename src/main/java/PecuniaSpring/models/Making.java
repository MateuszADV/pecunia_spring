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
@Table(name = "makings")
public class Making {
    @SequenceGenerator(
            name = "makings_sequence",
            sequenceName = "actives_sequence",
            allocationSize = 1
    )
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "maknigs_sequence"
    )
    private Long id;
    private String making;
    @Column(name = "making_pl")
    private String makingPl;
    private String description;
    @Column(name = "created_at")
    private Timestamp created_at;
    @Column(name = "updated_at")
    private Timestamp updated_at;

//    @OneToMany(mappedBy = "makings", cascade = {CascadeType.MERGE, CascadeType.PERSIST})
//    private List<Note> notes;
}
