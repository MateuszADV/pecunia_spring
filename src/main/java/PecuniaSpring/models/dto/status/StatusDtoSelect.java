package PecuniaSpring.models.dto.status;

import lombok.*;

import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class StatusDtoSelect {

    @NotNull(message = "SELECT STATUS")
    private Long id;
    private String status;
    private String statusPl;
    private String description;
    private Timestamp created_at;
    private Timestamp updated_at;
}
