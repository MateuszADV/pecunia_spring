package PecuniaSpring.models.sqlClass;

import PecuniaSpring.models.dto.making.MakingDto;
import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class GetNotesByStatus extends GetByStatus{
    private Long noteId;
    private MakingDto makings;
    private Integer width;
    private Integer height;
}
