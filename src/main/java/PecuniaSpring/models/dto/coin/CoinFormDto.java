package PecuniaSpring.models.dto.coin;

import PecuniaSpring.models.dto.CommonFormDto;
import PecuniaSpring.models.dto.active.ActiveDtoSelect;
import PecuniaSpring.models.dto.bought.BoughtDtoSelect;
import PecuniaSpring.models.dto.currency.CurrencyDto;
import PecuniaSpring.models.dto.imageType.ImageTypeDtoSelect;
import PecuniaSpring.models.dto.quality.QualityDtoSelect;
import PecuniaSpring.models.dto.status.StatusDtoSelect;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.sql.Date;
import java.sql.Timestamp;

@AllArgsConstructor
@Getter
@Setter
@ToString
public class CoinFormDto extends CommonFormDto {

    public CoinFormDto(String itemDate, Date dateBuy, String nameCurrency, Double priceBuy, Double priceSell,
                       Integer quantity, String statusSell, String description, String aversPath, String reversePath,
                       Double denomination, String series, String composition, Double diameter, Double thickness, Double weight,
                       Timestamp created_at, Timestamp updated_at,
                       Boolean visible, String unitCurrency, String unitQuantity, BoughtDtoSelect boughts, QualityDtoSelect qualities, StatusDtoSelect statuses,
                       ActiveDtoSelect actives, ImageTypeDtoSelect imageTypes) {
        super( itemDate, dateBuy, nameCurrency, priceBuy, priceSell, quantity, statusSell, description,
                unitQuantity, visible, unitCurrency, aversPath, reversePath, created_at, updated_at, boughts, qualities, statuses, actives, imageTypes);
        this.denomination = denomination;
        this.composition = composition;
        this.diameter = diameter;
        this.thickness = thickness;
        this.weight = weight;
        this.series = series;
    }
    public CoinFormDto() {
    }
    private Long id;
    private Double denomination;
    private String series;
    private String composition;
    private Double diameter;
    private Double thickness;
    private Double weight;

    private CurrencyDto currencies;

}

