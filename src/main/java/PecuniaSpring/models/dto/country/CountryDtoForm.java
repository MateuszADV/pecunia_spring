package PecuniaSpring.models.dto.country;

import PecuniaSpring.models.dto.continent.ContinentDto;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.sql.Timestamp;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
//@JsonInclude(JsonInclude.Include.NON_NULL)    //nie wyświetla nulowych wartości
@JsonInclude(JsonInclude.Include.NON_EMPTY)  //nie wyswietla null i pustych wartości
public class CountryDtoForm {

    private Long id;
    private String continent;
    @NotNull
    @NotEmpty
    @Size(min=3, max=60)
    private String countryEn;
    @NotNull
    @NotEmpty
    @Size(min=3, max=60)
    private String countryPl;
    @NotNull
    @NotEmpty
    @Size(min=3, max=60)
    private String capital_city;
    @Pattern(regexp="([A-Z]{2})|^$", message = "Pole moze być puste lub zawierać kod alpha 2(np: AB)")
    private String alfa_2;
    @Pattern(regexp="([A-Z]{3})|^$", message = "Pole moze być puste lub zawierać kod alpha 3(np: ABC)")
    private String alfa_3;
    @Pattern(regexp="([0-9]{3})|^$", message = "Pole moze być puste lub zawierać kod numeryczby(np: 012)")
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
