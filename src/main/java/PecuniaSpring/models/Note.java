package PecuniaSpring.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;

@Getter
@Setter
@ToString
@Entity
@Table(name = "notes")
public class Note extends Common {

    public Note(String bought, String itemDate, Date dateBuy, String nameCurrency, Integer signatureCode, Double priceBuy, Double priceSell,
                Integer quantity, String status, String statusSell, String description, String imgType, String aversPath, String reversePath,
                Double denomination, String quality, String series, String making, Timestamp created_at, Timestamp updated_at) {
        super(bought, itemDate, dateBuy, nameCurrency, signatureCode, priceBuy, priceSell, quantity,quality, status, statusSell, description,
                imgType, aversPath, reversePath, created_at, updated_at);
        this.denomination = denomination;
        this.series = series;
        this.making = making;
    }
    public Note() {
    }

    @SequenceGenerator(
            name = "notes_sequence",
            sequenceName = "notes_sequence",
            allocationSize = 1
    )
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "notes_sequence"
    )
    private Long id;
    private Double denomination;
    private String series;
    private String making;

    @ManyToOne
    @JoinColumn(name = "currency_id")
    private Currency currencies;

}

