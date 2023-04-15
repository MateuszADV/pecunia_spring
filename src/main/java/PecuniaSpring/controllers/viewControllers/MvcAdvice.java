package PecuniaSpring.controllers.viewControllers;


import PecuniaSpring.models.other.ApiMetal;
import PecuniaSpring.models.other.ApiResponseInfo;
import PecuniaSpring.models.other.GetApiMetal;
import PecuniaSpring.services.apiService.ApiServiceImpl;
import com.sun.jersey.api.client.ClientResponse;
import lombok.NoArgsConstructor;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import utils.JsonUtils;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
@NoArgsConstructor
public class MvcAdvice {

    private ApiServiceImpl apiService;

    @Autowired
    public MvcAdvice(ApiServiceImpl apiService) {
        this.apiService = apiService;
    }

    @ModelAttribute
    public void addRateMetal(ModelMap modelMap) {
        GetApiMetal getApiMetal = new GetApiMetal();
        ApiResponseInfo apiResponseInfo = new ApiResponseInfo();
        List<ApiMetal> apiMetals = new ArrayList<>();

        try {
            ClientResponse clientResponse = apiService.clientResponse("https://api.metals.live/v1/spot");
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
            modelMap.addAttribute("getApiMetal", getApiMetal);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            modelMap.addAttribute("getApiMetal", getApiMetal);
        }

//        System.out.println(JsonUtils.gsonPretty(getApiMetal));
    }

    @ModelAttribute
    public void currencyRAte(ModelMap modelMap) {
        ClientResponse clientResponse = apiService.clientResponse("https://api.nbp.pl/api/exchangerates/tables/A/?format=json");
        String stringJson = clientResponse.getEntity(String.class);
        JSONArray jsonArray = new JSONArray(stringJson);
        System.out.println(clientResponse.getHeaders());
        System.out.println(JsonUtils.gsonPretty(jsonArray.getJSONObject(0).get("table")));
        System.out.println(JsonUtils.gsonPretty(jsonArray.getJSONObject(0).get("no")));
        System.out.println(JsonUtils.gsonPretty(jsonArray.getJSONObject(0).get("effectiveDate")));
        System.out.println(JsonUtils.gsonPretty(jsonArray.getJSONObject(0).getJSONArray("rates").getJSONObject(0)));
    }
}
