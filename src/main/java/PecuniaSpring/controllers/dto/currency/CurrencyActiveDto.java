package PecuniaSpring.controllers.dto.currency;

import PecuniaSpring.controllers.dto.active.ActiveDto;
import PecuniaSpring.controllers.dto.active.ActiveDtoCurrency;
import PecuniaSpring.controllers.dto.country.CountryGetDto;
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

public class CurrencyActiveDto {
    private Long id;
    @Pattern(regexp="([A-Z]{3})|^$", message = "Pole moze być puste lub zawierać kod waluty(np: ABC)")
    private String cod;
    @Pattern(regexp = "[A-Z]*", message = "Muszą być duze litery (NOTE, COIN, BOND...) ")
    @NotEmpty(message = "Pole nie może być puste (NOTE, COIN, BOND...) ")
    private String pattern;
    @NotEmpty(message = "Pole nie może byc EMPTY")
    private String currency;
    private String change;
    private Integer active;
    private String dataExchange;
    @Size(min = 5, message = "Minimalna długoś opisu seri to 5 znaków")
    private String currencySeries;
    private String currencyFrom;
    private String converter;
    private String description;
    private Timestamp created_at;
    private Timestamp updated_at;
    @NotNull
    @Valid
    private ActiveDtoCurrency actives;
    private CountryGetDto countries;
}
