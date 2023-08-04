package PecuniaSpring.controllers.apiControllers;

import PecuniaSpring.models.repositories.CountryRepository;
import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.yaml.snakeyaml.Yaml;
import utils.JsonUtils;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "/api/v1")
@AllArgsConstructor
public class ChartApiController {

    private CountryRepository countryRepository;

    @GetMapping("/report")
    public ResponseEntity<Object> yamlToJson(@RequestParam("report") String report) {
        /**
         * Dane pobrane z bazy danych
         */
        //TODO przerobic na funkcje któta pobiera paramety i zwraca object z wartościami do wyswietlenia na wykresie dane jedno lub więcej wartościowe
        List<Object[]> objects = countryRepository.reportCountCountry();
        List<String> labels = new ArrayList();
        List<Integer> data = new ArrayList();
        for (Object[] object : objects) {
            labels.add(object[0].toString());
            data.add(Integer.valueOf(object[1].toString()));
        }

        Yaml yaml = new Yaml();
        InputStream inputStream = this.getClass()
                .getClassLoader()
                .getResourceAsStream("static/reportsChart/reports/" + report + ".yml");
        Map<String, Object> obj = yaml.load(inputStream);

        JSONObject jsonObject = new JSONObject(obj);

        /**
        * Dodawanie danych z bazy do wyświetlenia na wykresie
        **/
        //TODO dodać funkcje która zwraca object z już dodanymi wartościami do wyswietlenia
        jsonObject.getJSONObject("chart").put("labels", labels);
        jsonObject.getJSONObject("chart").getJSONObject("datasets").put("data", data);

        Object object = new Gson().fromJson(String.valueOf(jsonObject), Object.class);
        System.out.println(JsonUtils.gsonPretty(object));
        return ResponseEntity.ok().body(object);
    }
}
