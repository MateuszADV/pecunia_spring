package PecuniaSpring.services.apiService;

import PecuniaSpring.models.other.ApiMetal;
import PecuniaSpring.models.other.ApiResponseInfo;
import PecuniaSpring.models.other.GetApiMetal;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import org.json.JSONArray;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Service
public class ApiServiceImpl implements ApiService {
    @Override
    public ClientResponse clientResponse(String url) {
        Client client = Client.create();
        WebResource webResource = client.resource(url);
        ClientResponse clientResponse = webResource.accept("application/json")
                .get(ClientResponse.class);

        if (clientResponse.getStatus() == 200) {
            client.destroy();
            return clientResponse;
        }
        else{
            throw new RuntimeException("Błąd pobrania... " + clientResponse.getStatusInfo() + " - " + clientResponse.getStatus());
        }
    }

    @Override
    public GetApiMetal getApiMetal(String url) {
        GetApiMetal getApiMetal = new GetApiMetal();
        ApiResponseInfo apiResponseInfo = new ApiResponseInfo();
        List<ApiMetal> apiMetals = new ArrayList<>();

        try {
            ClientResponse clientResponse = clientResponse(url);
            String stringJson = clientResponse.getEntity(String.class);
            JSONArray jsonArray = new JSONArray(stringJson);

            apiResponseInfo.setResponseStatusInfo(clientResponse.getStatusInfo());
            getApiMetal.setApiResponseInfo(apiResponseInfo);

            String metal;
            Float price;
            for(int i = 0; i < jsonArray.length(); i++) {
                if ((jsonArray.getJSONObject(i).names().get(0).toString()).contains("timestamp")) {
                    Long timeStamp = (Long) jsonArray.getJSONObject(i).get("timestamp");
                    Timestamp stamp = new Timestamp(timeStamp);
                    Date date = new Date(stamp.getTime());
                    apiResponseInfo.setDate(date);
                } else {
                    metal = jsonArray.getJSONObject(i).names().get(0).toString();
                    price = Float.parseFloat(jsonArray.getJSONObject(i).get(metal).toString());
                    apiMetals.add(new ApiMetal(metal, price));
                }
            }
            getApiMetal.setApiMetals(apiMetals);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return getApiMetal;
    }
}
