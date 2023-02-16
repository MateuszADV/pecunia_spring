package PecuniaSpring.models.dto.status;

import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class StatusDtoForm {

    private Long id;
    @NotEmpty(message = "Wartość nie może być pusta")
    @Size(min = 3, message = "Minimalna dlugość to 3 znamki" )
    private String status;
    private String statusPl;
    private String description;
    private Timestamp created_at;
    private Timestamp updated_at;
}
