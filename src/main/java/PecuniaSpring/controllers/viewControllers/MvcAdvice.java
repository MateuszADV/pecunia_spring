package PecuniaSpring.controllers.viewControllers;


import com.google.gson.JsonObject;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.Date;

@ControllerAdvice
public class MvcAdvice {
    @ModelAttribute
    public void addRateMetal(ModelMap modelMap) {
        modelMap.addAttribute("standardDate", new Date());

        Client client = Client.create();
        WebResource webResource = client.resource("https://api.metals.live/v1/spot");
        ClientResponse clientResponse = webResource.accept("application/json")
                .get(ClientResponse.class);
        if(clientResponse.getStatus() !=200){
            System.out.println( "Cos poszło ni tak!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
            throw new RuntimeException("Błąd pobrania... " + clientResponse.getStatus());
        }

        client.destroy();

        String stringJson = clientResponse.getEntity(String.class);
        System.out.println(clientResponse);
        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray = new JSONArray(stringJson);
        System.out.println(stringJson);
        System.out.println(jsonArray.getJSONObject(0));
        System.out.println(jsonArray.getJSONObject(0).names());


        System.out.println("----------------------------------------------------");
        for(int i = 0; i < jsonArray.length(); i++) {
            System.out.println(jsonArray.getJSONObject(i).names().get(0));
            System.out.println(jsonArray.getJSONObject(i).get(jsonArray.getJSONObject(i).names().get(0).toString()));
        }
    }
}
