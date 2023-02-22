package PecuniaSpring.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;

@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "notes")
public class Note extends Common {


    public Note(String bought, String itemDate, Date dateBuy, String nameCurrency, Integer signatureCode, Double priceBuy, Double priceSell,
                Integer quantity, String status, String statusSell, String description, String imgType, String aversPath, String reversePath,
                Double denomination, String quality, String series, Integer width, Integer height, Timestamp created_at, Timestamp updated_at,
                Boolean visible, String unitCurrency, String unitQuantity, Bought boughts, Quality qualities, Status statuses) {
        super(bought, itemDate, dateBuy, nameCurrency, signatureCode, priceBuy, priceSell, quantity, quality, status, statusSell, description,
                imgType, unitQuantity, visible, unitCurrency, aversPath, reversePath, created_at, updated_at, boughts, qualities, statuses);
        this.denomination = denomination;
        this.series = series;
//        this.making = making;
        this.width = width;
        this.height = height;
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
    @Column(name = "denomination")
    private Double denomination;
    @Column(name = "series")
    private String series;
    @Column(name = "making")
    private String making;
    @Column(name = "width")
    private Integer width;
    @Column(name = "height")
    private Integer height;

    @ManyToOne
    @JoinColumn(name = "currency_id")
    private Currency currencies;

    @ManyToOne
    @JoinColumn(name = "active_id")
    private Active actives;

    @ManyToOne
    @JoinColumn(name = "making_id")
    private Making makings;
}

