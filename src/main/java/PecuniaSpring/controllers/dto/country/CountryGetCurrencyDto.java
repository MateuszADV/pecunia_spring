package PecuniaSpring.controllers.dto.country;

import PecuniaSpring.controllers.dto.continent.ContinentDto;
import PecuniaSpring.controllers.dto.currency.CurrencyDto;
import PecuniaSpring.models.Currency;
import lombok.*;

import java.sql.Timestamp;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class CountryGetCurrencyDto {
    private Long id;
    private String continent;
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

//    private ContinentDto continents;
    private List<CurrencyDto> currencies;
}
