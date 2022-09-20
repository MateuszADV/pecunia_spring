package PecuniaSpring.controllers.dto.continent;

import PecuniaSpring.models.Country;
import lombok.*;

import java.sql.Timestamp;
import java.util.List;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class continentDto {
    private Long id;
    private String continentEn;
    private String continentPl;
    private String continentCode;
    private Timestamp created_at;
    private Timestamp updated_at;

    private List<Country> countries;
}
