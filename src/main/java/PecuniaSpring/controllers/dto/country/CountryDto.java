package PecuniaSpring.controllers.dto.country;

import PecuniaSpring.controllers.dto.continent.ContinentDto;
import PecuniaSpring.models.Continent;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.sql.Timestamp;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
//@JsonInclude(JsonInclude.Include.NON_NULL)    //nie wyświetla nulowych wartości
@JsonInclude(JsonInclude.Include.NON_EMPTY)  //nie wyswietla null i pustych wartości
public class CountryDto {

    private Long id;
    private String continent;
    @NotNull
    @NotEmpty
    @Size(min=3, max=30)
    private String countryEn;
    private String countryPl;
    private String capital_city;
    private String alfa_2;
    private String alfa_3;
    private String numeric_code;
    private String iso_code;
    private Boolean exists;
    private String description;
    private Timestamp created_at;
    private Timestamp updated_at;
    @NotNull
    @Valid
    private ContinentDto continents;
}
