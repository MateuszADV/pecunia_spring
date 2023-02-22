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
@Table(name = "image_types")
public class ImageType {

    @SequenceGenerator(
            name = "image_types_sequence",
            sequenceName = "image_types_sequence",
            allocationSize = 1
    )
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "image_types_sequence"
    )
    private Long id;
    private String type;
    @Column(name = "type_pl")
    private String typePl;
    private String description;
    @Column(name = "created_at")
    private Timestamp created_at;
    @Column(name = "updated_at")
    private Timestamp updated_at;

//    @OneToMany(mappedBy = "image_types", cascade = {CascadeType.MERGE, CascadeType.PERSIST})
//    private List<Note> notes;
}
