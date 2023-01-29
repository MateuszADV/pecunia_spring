package PecuniaSpring.models.dto.note;

import PecuniaSpring.models.dto.bought.BoughtDtoNote;
import PecuniaSpring.models.dto.currency.CurrencyDto;
import PecuniaSpring.models.dto.CommonFormDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.sql.Date;
import java.sql.Timestamp;

@AllArgsConstructor
@Getter
@Setter
@ToString
public class NoteFormDto extends CommonFormDto {


    public NoteFormDto(String bought, String itemDate, Date dateBuy, String nameCurrency, Integer signatureCode, Double priceBuy, Double priceSell,
                       Integer quantity, String status, String statusSell, String description, String imgType, String aversPath, String reversePath,
                       Double denomination, String quality, String series, String making, Integer width, Integer height, Timestamp created_at, Timestamp updated_at,
                       Boolean visible, String unitCurrency, String unitQuantity ) {
        super(bought, itemDate, dateBuy, nameCurrency, signatureCode, priceBuy, priceSell, quantity, quality, status, statusSell, description,
                imgType, unitQuantity, visible, unitCurrency, aversPath, reversePath, created_at, updated_at);
        this.denomination = denomination;
        this.series = series;
        this.making = making;
        this.width = width;
        this.height = height;
    }
    public NoteFormDto() {
    }

    private CurrencyDto currencies;
    @NotNull
    @Valid
    private BoughtDtoNote boughts;

    private Long id;
    private Double denomination;
    private String series;
    private String making;
    private Integer width;
    private Integer height;


}

