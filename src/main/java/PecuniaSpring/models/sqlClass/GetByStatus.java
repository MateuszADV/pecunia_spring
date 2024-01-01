package PecuniaSpring.models.sqlClass;


import PecuniaSpring.models.dto.pattern.PatternDto;
import PecuniaSpring.models.dto.quality.QualityDto;
import lombok.*;

import javax.persistence.MappedSuperclass;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
@MappedSuperclass
public abstract class GetByStatus {
    private Long countryId;
    private String countryEn;
    private String countryPl;
    private Long currencyId;
    private String currencySeries;
    private PatternDto patterns;
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
    private String description;
    private String aversPath;
    private String reversePath;

}
