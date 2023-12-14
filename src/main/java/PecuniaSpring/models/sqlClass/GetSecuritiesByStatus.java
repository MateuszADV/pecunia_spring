package PecuniaSpring.models.sqlClass;

import PecuniaSpring.models.dto.making.MakingDto;
import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class GetSecuritiesByStatus extends GetByStatus {
    private Long securityId;
    private MakingDto makings;
    private Integer width;
    private Integer height;
}
