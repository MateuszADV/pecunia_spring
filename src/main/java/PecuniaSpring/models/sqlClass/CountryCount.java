package PecuniaSpring.models.sqlClass;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class CountryCount {
    private Long continentId;
    private String continentEn;
    private String continentPl;
    private String code;
    private Integer total;
}
