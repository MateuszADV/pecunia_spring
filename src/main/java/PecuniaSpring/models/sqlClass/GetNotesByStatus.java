package PecuniaSpring.models.sqlClass;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class GetNotesByStatus {
    private Long countryId;
    private String countryEn;
    private String countryPl;
    private Long currencyId;
    private String currencySeries;
    private String bought;
    private Double denomination;
    private String nameCurrency;
    private String itemDate;
    private Double priceBuy;
    private Integer quantity;
//    private String quality;
    private String description;
    private String aversPath;
    private String  reversePath;
}
