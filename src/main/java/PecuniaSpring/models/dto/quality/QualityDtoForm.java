package PecuniaSpring.models.dto.quality;

import lombok.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class QualityDtoForm {
    private Long id;
    @NotEmpty(message = "Wartość nie może być pusta")
    @Size(min = 3, message = "Nazwa musi być dłuższa niż 3 znaki")
    private String quality;
    private String qualityPl;
    private String description;
    private Timestamp created_at;
    private Timestamp updated_at;
}
