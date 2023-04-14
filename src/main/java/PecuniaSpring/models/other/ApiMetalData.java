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
public class ApiMetalData {
    private Date date;
//    private Integer responseStatus;
    private Response.StatusType responseStatusInfo;
    private List<ApiMetal> apiMetals = new ArrayList<>();

    public void getResponseStatusInfo(Response.StatusType statusInfo) {
    }
}
