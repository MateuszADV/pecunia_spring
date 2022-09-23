package PecuniaSpring.controllers.dto.continent;

import PecuniaSpring.controllers.dto.country.CountryGetDto;
import lombok.*;

import java.sql.Timestamp;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ContinentCountriesDto {
    private Long id;
    private String continentEn;
    private String continentPl;
    private String continentCode;
    private Timestamp created_at;
    private Timestamp updated_at;
    private List<CountryGetDto> countries;
}
