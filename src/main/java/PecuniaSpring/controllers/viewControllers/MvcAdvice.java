package PecuniaSpring.controllers.viewControllers;


import PecuniaSpring.models.other.ApiMetal;
import PecuniaSpring.models.other.ApiMetalData;
import com.google.gson.JsonObject;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import utils.JsonUtils;

import javax.ws.rs.core.Response;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class MvcAdvice {
    @ModelAttribute
    public void addRateMetal(ModelMap modelMap) {

        Client client = Client.create();
        WebResource webResource = client.resource("https://api.metals.live/v1/spot");
        ClientResponse clientResponse = webResource.accept("application/json")
                .get(ClientResponse.class);
        if(clientResponse.getStatus() !=200){
            System.out.println( "Cos poszło ni tak!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
            System.out.println(clientResponse.getHeaders());
            System.out.println(clientResponse.getStatus());
            System.out.println(clientResponse.getStatusInfo());
            throw new RuntimeException("Błąd pobrania... " + clientResponse.getStatus());
        }

        client.destroy();
        System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        System.out.println(clientResponse);
        System.out.println(clientResponse.getStatus());
        System.out.println(clientResponse.getStatusInfo());
        System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");

        String stringJson = clientResponse.getEntity(String.class);

        JSONArray jsonArray = new JSONArray(stringJson);

        List<ApiMetal> apiMetals = new ArrayList<>();
        ApiMetalData apiMetalData = new ApiMetalData();
        apiMetalData.setResponseStatusInfo(clientResponse.getStatusInfo());

        String metal;
        Float price;
        System.out.println("----------------------------------------------------");
        for(int i = 0; i < jsonArray.length(); i++) {
            if ((jsonArray.getJSONObject(i).names().get(0).toString()).contains("timestamp")) {
                Long timeStamp = (Long) jsonArray.getJSONObject(i).get("timestamp");
                Timestamp stamp = new Timestamp(timeStamp);
                Date date = new Date(stamp.getTime());
                apiMetalData.setDate(date);
                modelMap.addAttribute("standardDate", date);
                modelMap.addAttribute("dateMetal", date);
            } else {
                metal = jsonArray.getJSONObject(i).names().get(0).toString();
                price = Float.parseFloat(jsonArray.getJSONObject(i).get(metal).toString());
                apiMetals.add(new ApiMetal(metal, price));

            }
        }
        System.out.println("===================================================");
        apiMetalData.setApiMetals(apiMetals);
        System.out.println(JsonUtils.gsonPretty(apiMetalData));
        System.out.println(apiMetalData.getResponseStatusInfo().getStatusCode());
        System.out.println(apiMetalData.getResponseStatusInfo().getReasonPhrase());
        System.out.println(apiMetalData.getResponseStatusInfo().getFamily());
        System.out.println("===================================================");

        modelMap.addAttribute("apiMetal", apiMetalData);
        modelMap.addAttribute("apiMetalJson", JsonUtils.gsonPretty(apiMetalData));
    }
}
