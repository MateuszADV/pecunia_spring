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
@Table(name = "qualities")
public class Quality {
    @SequenceGenerator(
            name = "qualities_sequence",
            sequenceName = "qualities_sequence",
            allocationSize = 1
    )
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "qualities_sequence"
    )
    private Long id;
    private String quality;
    @Column(name = "quality_pl")
    private String qualityPl;
    private String description;
    @Column(name = "created_at")
    private Timestamp created_at;
    @Column(name = "updated_at")
    private Timestamp updated_at;

    @OneToMany(mappedBy = "qualities", cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    private List<Note> notes;
}
