package PecuniaSpring.models.sqlClass;

import PecuniaSpring.models.dto.quality.QualityDto;
import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class GetCoinsByStatus {
    private Long coinId;
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
    private Double priceSell;
    private Integer quantity;
    private String unitQuantity;
    private QualityDto qualities;
    private Boolean visible;
    private String composition;
    private Double diameter;
    private Double thickness;
    private Double weight;
    private String description;
    private String aversPath;
    private String  reversePath;
}