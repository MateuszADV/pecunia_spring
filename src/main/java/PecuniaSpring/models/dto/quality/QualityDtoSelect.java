package PecuniaSpring.models.dto.quality;

import lombok.*;

import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class QualityDtoSelect {
    @NotNull(message = "SELECT QUALITY")
    private Long id;
    private String quality;
    private String qualityPl;
    private String description;
    private Timestamp created_at;
    private Timestamp updated_at;
}
