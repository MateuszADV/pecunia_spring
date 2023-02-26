package PecuniaSpring.models.dto.imageType;

import lombok.*;

import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class ImageTypeDtoSelect {

    @NotNull(message = "SELECT IMAGE TYPE")
    private Long id;
    private String type;
    private String typePl;
    private String description;
    private Timestamp created_at;
    private Timestamp updated_at;
}
