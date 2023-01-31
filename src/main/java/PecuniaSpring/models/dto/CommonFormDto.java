package PecuniaSpring.models.dto;

import lombok.*;

import javax.validation.constraints.*;
import javax.persistence.MappedSuperclass;
//import javax.validation.constraints.*;
import java.sql.Date;
import java.sql.Timestamp;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
@MappedSuperclass
public abstract class CommonFormDto {

    private String bought;          //Miejsce Zakupu
    private String itemDate;
    private Date dateBuy;           //Data  zakupu/ Data utworzenia seta banknotów
    private String nameCurrency;        //Nazwa waluty/ Nazwa Seta
    private Integer signatureCode;      // Sygnatura banknotu/ oznaczenie Seta (Opcjonalne narazie - 999 kod doseta określający różne banknoty)
//    @Pattern(regexp = "(^[0-9]*\\.[0-9]{2}$)", message = "To nie jest liczba")
    @Digits(integer=6, fraction = 2, message = "Podaj poprawną cenę (1.23)")
    @DecimalMin(value = "0.00", message = "Wartość nie może być ujemna")
    @NotNull(message = "Wartość nie może być pusta")
    private Double priceBuy;            //Cena zakupu
    @Digits(integer=6, fraction = 2, message = "Podaj poprawną cenę (1.23)")
    @DecimalMin(value = "0.00", message = "Wartość nie może być ujemna")
    @NotNull(message = "Wartość nie może być pusta")
    private Double priceSell;           //Cena Sprzedaży (sugerowana)
//    @Digits(integer=6, fraction = 2)
    @PositiveOrZero(message = "Wartość >=0 ")
    @NotNull(message = "Wartość nie może być NULL")
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
}
