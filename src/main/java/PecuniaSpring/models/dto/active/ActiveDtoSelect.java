package PecuniaSpring.models.dto.active;

import lombok.*;

import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class ActiveDtoSelect {
    @NotNull(message = "Select Active")
    private Long id;
    private Integer activeCod;
    private String name;
    private String description;
    private Timestamp created_at;
    private Timestamp updated_at;
}
