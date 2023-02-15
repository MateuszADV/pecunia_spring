package PecuniaSpring.models.dto.note;

import PecuniaSpring.models.dto.CommonDto;
import PecuniaSpring.models.dto.active.ActiveDto;
import PecuniaSpring.models.dto.bought.BoughtDto;
import PecuniaSpring.models.dto.currency.CurrencyDto;
import PecuniaSpring.models.dto.making.MakingDto;
import PecuniaSpring.models.dto.quality.QualityDto;
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
public class NoteDtoByCurrency extends CommonDto {


    public NoteDtoByCurrency(String itemDate, Date dateBuy, String nameCurrency, Double priceBuy, Double priceSell,
                             Integer quantity, String status, String statusSell, String description, String imgType, String aversPath, String reversePath,
                             Double denomination, String quality, String series, String making, Integer width, Integer height, Timestamp created_at, Timestamp updated_at,
                             Boolean visible, String unitCurrency, String unitQuantity, BoughtDto boughts, QualityDto qualities) {
        super(itemDate, dateBuy, nameCurrency, priceBuy, priceSell, quantity, quality, status, statusSell, description,
                imgType, unitQuantity, visible, unitCurrency, aversPath, reversePath, created_at, updated_at, boughts, qualities);
        this.denomination = denomination;
        this.series = series;
        this.making = making;
        this.width = width;
        this.height = height;
    }
    public NoteDtoByCurrency() {
    }

    private Long id;
    private Double denomination;
    private String series;
    private String making;
    private Integer width;
    private Integer height;

    private ActiveDto actives;
    private MakingDto makings;

//    private CurrencyDto currencies;
}

