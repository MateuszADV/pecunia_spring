package PecuniaSpring.models.dto.pattern;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
//@JsonInclude(JsonInclude.Include.NON_EMPTY)  //nie wyswietla null i pustych wartości
public class PatternDto {

    private Long id;
    @Pattern(regexp = "[A-Z]*", message = "Muszą być duze litery (NOTE, COIN, BOND...) ")
    @NotEmpty(message = "Pole nie może być puste (NOTE, COIN, BOND...) ")
    private String pattern;
    private String name;
    private String description;
    private Timestamp created_at;
    private Timestamp updated_at;
}
