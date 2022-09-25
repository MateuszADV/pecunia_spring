package PecuniaSpring.controllers.dto.continent;

import lombok.*;

import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.List;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ContinentDto {
    @NotNull
    private Long id;
    private String continentEn;
    private String continentPl;
    private String continentCode;
    private Timestamp created_at;
    private Timestamp updated_at;
}
