package PecuniaSpring.models.dto.making;

import lombok.*;

import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class MakingDtoForm {
    private Long id;
    private String making;
    private String makingPl;
    private String description;
    private Timestamp created_at;
    private Timestamp updated_at;
}
