package PecuniaSpring.controllers.dto.currency;

import PecuniaSpring.controllers.dto.active.ActiveDto;
import PecuniaSpring.controllers.dto.country.CountryGetDto;
import lombok.*;

import java.sql.Timestamp;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode

public class CurrencyActiveDto {
    private Long id;
    private String cod;
    private String pattern;
    private String currency;
    private String change;
    private Integer active;
    private String dataExchange;
    private String currencySeries;
    private String currencyFrom;
    private String converter;
    private String description;
    private Timestamp created_at;
    private Timestamp updated_at;

    private ActiveDto actives;
    private CountryGetDto countries;
}
