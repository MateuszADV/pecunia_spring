package PecuniaSpring.controllers.apiControllers;

import PecuniaSpring.models.repositories.CountryRepository;
import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.yaml.snakeyaml.Yaml;
import utils.JsonUtils;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import static com.fasterxml.jackson.databind.type.LogicalType.Map;

@RestController
@RequestMapping(path = "/api/v1")
@AllArgsConstructor
public class ChartApiController {

    private CountryRepository countryRepository;

    @GetMapping("/report")
    public ResponseEntity<Object> getAllCountry() {
        List<Object[]> objects = countryRepository.reportCountCountry();
        System.out.println(JsonUtils.gsonPretty(objects));
        List<String> labels = new ArrayList();
        List<Integer> data = new ArrayList();
        for (Object[] object  objects) {
            labels.add(object[0].toString());
            data.add(Integer.valueOf(object[1].toString()));
        }


        System.out.println(data.toString());
        System.out.println(labels);

        System.out.println("********************* TES YAML FILE************************");

        Yaml yaml = new Yaml();
        InputStream inputStream = this.getClass()
                .getClassLoader()
                .getResourceAsStream("static/reportsChart/reports/my_report.yml");
        Map(String, Object) obj = yaml.load(inputStream);
        System.out.println();

        System.out.println(JsonUtils.gsonPretty(obj));
        System.out.println(++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++);
        JSONObject jsonObject = new JSONObject(obj);

        jsonObject.getJSONObject("chart").put("labels", labels);
        jsonObject.getJSONObject("chart").getJSONObject("datasets").put("data", data);

        Object object2 = new Gson().fromJson(String.valueOf(jsonObject), Object.class);
        modelMap.addAttribute(chartData, object2);
        System.out.println(object2);
        System.out.println(JsonUtils.gsonPretty(object2));
        return ResponseEntity.ok().body(object);
    }
}
