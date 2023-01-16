package PecuniaSpring.models.dto.currency;

import PecuniaSpring.models.dto.country.CountryDto;
import PecuniaSpring.models.dto.note.NoteDto;
import lombok.*;

import java.sql.Timestamp;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class CurrencyDto {

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

//    private CountryDto countries;
//    private List<NoteDto> notes;
}
