package PecuniaSpring.models.dto.statusOrder;

import lombok.*;

import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class StatusOrderDtoSelect {
    @NotNull(message = "Status Order Active")
    private Long id;
    private String statusEn;
    private String statusPl;
    private String description;
    private Timestamp created_at;
    private Timestamp updated_at;
}
