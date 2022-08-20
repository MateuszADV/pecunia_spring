package PecuniaSpring.controllers.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

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
    private String countryEn;
    private String country_pl;
    private String capital_city;
    private String alfa_2;
    private String alfa_3;
    private String numeric_code;
    private String iso_code;
    private Boolean exists;
    private String description;
//    private Timestamp created_at;
//    private Timestamp updated_at;
}
