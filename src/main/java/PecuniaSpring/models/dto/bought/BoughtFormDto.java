package PecuniaSpring.models.dto.bought;

import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class BoughtFormDto {
    private Long id;
    @NotEmpty(message = "Wartość nie może być pusta")
    private String name;
    private String fullName;
    private String description;
    private Timestamp created_at;
    private Timestamp updated_at;

}
