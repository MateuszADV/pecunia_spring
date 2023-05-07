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
       GetApiMetal getApiMetal = apiService.getApiMetal("https://api.metals.live/v1/spot");
       modelMap.addAttribute("getApiMetal", getApiMetal);
//        System.out.println(JsonUtils.gsonPretty(getApiMetal));
    }

    @ModelAttribute
    public void currencyRAte() {
//        ClientResponse clientResponse = apiService.clientResponse("https://api.nbp.pl/api/exchangerates/tables/A/?format=json");
//        String stringJson = clientResponse.getEntity(String.class);
//        JSONArray jsonArray = new JSONArray(stringJson);
//        System.out.println(clientResponse.getHeaders());
//        System.out.println(JsonUtils.gsonPretty(jsonArray.getJSONObject(0).get("table")));
//        System.out.println(JsonUtils.gsonPretty(jsonArray.getJSONObject(0).get("no")));
//        System.out.println(JsonUtils.gsonPretty(jsonArray.getJSONObject(0).get("effectiveDate")));
//        System.out.println(JsonUtils.gsonPretty(jsonArray.getJSONObject(0).getJSONArray("rates").getJSONObject(0)));
    }
}
