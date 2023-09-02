package PecuniaSpring.models.dto.active;

import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class ActiveDtoForm {
    private Long id;
    @NotNull(message = "Pdaj kod actiwe np:(123)")
    private Integer activeCod;
    @NotEmpty(message = "podaj Opis")
    @NotNull(message = "Opis musi być podany")
    private String name;
    private String description;
    private Timestamp created_at;
    private Timestamp updated_at;
}
