package PecuniaSpring.models.other;

import lombok.*;

import javax.ws.rs.core.Response;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ApiResponseInfo {
    private Date date;
    private Response.StatusType responseStatusInfo;
}
