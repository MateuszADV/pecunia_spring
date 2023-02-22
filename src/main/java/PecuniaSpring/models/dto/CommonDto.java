package PecuniaSpring.models.dto;

import PecuniaSpring.models.dto.bought.BoughtDto;
import PecuniaSpring.models.dto.quality.QualityDto;
import PecuniaSpring.models.dto.status.StatusDto;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.sql.Date;
import java.sql.Timestamp;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
@MappedSuperclass
public abstract class CommonDto {

//    private String bought;          //DO USUNIĘCIA Miejsce Zakupu
    private String itemDate;
    private Date dateBuy;           //Data zakupu/ Data utworzenia seta banknotów
    private String nameCurrency;        //Nazwa waluty/ Nazwa Seta
//    private Integer signatureCode;      //DO USUNIĘCIA Sygnatura banknotu/ oznaczenie Seta (Opcjonalne narazie - 999 kod doseta określający różne banknoty)
    private Double priceBuy;            //Cena zakupu
    private Double priceSell;           //Cena Sprzedaży (sugerowana)
    private Integer quantity;           //ilość
    private String unitQuantity;       //Określa rodzaj ilości(szt, set...)
    private String quality;             //Stan Itemu
    private String status;              // Status (kolekcja, na sprzedaż, sprzedane
    private String statusSell;          //StatusSell odpowiada za to czy dany banknot został wystawiony na sprzedaż
    private String description;         //Opis
    private String imgType;             //Typ obrazka(skan, foto, ze strony)
    private Boolean visible;            //Określa czy dany element jest widoczny dla wszystkich
    private String unitCurrency;
    private String aversPath;
    private String  reversePath;
    private Timestamp created_at;
    private Timestamp updated_at;

    private BoughtDto boughts;
    private QualityDto qualities;
    private StatusDto statuses;

}
