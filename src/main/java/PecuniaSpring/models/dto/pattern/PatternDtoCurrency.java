package PecuniaSpring.models.dto.pattern;

import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class PatternDtoCurrency {

    @NotNull(message = "Select Pattern")
    private Long id;
    private String pattern;
    private String name;
    private String description;
    private Timestamp created_at;
    private Timestamp updated_at;
}
