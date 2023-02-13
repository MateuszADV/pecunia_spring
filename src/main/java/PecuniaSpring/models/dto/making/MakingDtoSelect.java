package PecuniaSpring.models.dto.making;

import lombok.*;

import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class MakingDtoSelect {
    @NotNull(message = "SELECT MAKING")
    private Long id;
    private String making;
    private String makingPl;
    private String description;
    private Timestamp created_at;
    private Timestamp updated_at;
}
