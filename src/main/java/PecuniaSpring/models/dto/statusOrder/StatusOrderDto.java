package PecuniaSpring.models.dto.statusOrder;

import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class StatusOrderDto {
    private Long id;
    private String statusEn;
    private String statusPl;
    private String description;
    private Timestamp created_at;
    private Timestamp updated_at;
}
