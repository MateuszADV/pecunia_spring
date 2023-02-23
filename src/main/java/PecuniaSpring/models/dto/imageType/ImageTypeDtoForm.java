package PecuniaSpring.models.dto.imageType;

import lombok.*;

import javax.validation.constraints.NotEmpty;
import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class ImageTypeDtoForm {

    private Long id;
    @NotEmpty(message = "Pole nie może być puste")
    private String type;
    private String typePl;
    private String description;
    private Timestamp created_at;
    private Timestamp updated_at;
}
