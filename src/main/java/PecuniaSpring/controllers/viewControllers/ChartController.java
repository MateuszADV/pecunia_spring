package PecuniaSpring.controllers.viewControllers;

import PecuniaSpring.models.repositories.CountryRepository;
import com.google.gson.Gson;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.yaml.snakeyaml.Yaml;
import utils.JsonUtils;

import java.io.*;;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class ChartController {

    private CountryRepository countryRepository;

    @Autowired
    public ChartController(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }

    @GetMapping("/chart")
    public String getIndex(ModelMap modelMap) throws IOException {
        List<Object[]> objects = countryRepository.reportCountCountry();
//        System.out.println(JsonUtils.gsonPretty(objects));
        List<String> labels = new ArrayList<>();
        List<Integer> data = new ArrayList<>();
        for (Object[] object : objects) {
            labels.add(object[0].toString());
            data.add(Integer.valueOf(object[1].toString()));
        }

        modelMap.addAttribute("data", data);
        modelMap.addAttribute("labels", labels);


        Yaml yaml = new Yaml();
        InputStream inputStream = this.getClass()
                .getClassLoader()
                .getResourceAsStream("static/reportsChart/reports/my_report_continents_test.yml");
        Map<String, Object> obj = yaml.load(inputStream);

        System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        JSONObject jsonObject = new JSONObject(obj);

        /**
         * Dodawanie danych z bazy do wyświetlenia na wykresie
         **/
        jsonObject.getJSONObject("chart").put("labels",labels);
        jsonObject.getJSONObject("chart").getJSONObject("datasets").put("data", data);

//        System.out.println(JsonUtils.gsonPretty(jsonObject.getJSONObject("chart").getJSONObject("options")));
//        Object jsonObj = new Gson().fromJson(String.valueOf(jsonObject.getJSONObject("chart").getJSONObject("options")), Object.class);
//        modelMap.addAttribute("jsonOpt",jsonObj);
        Object object = new Gson().fromJson(String.valueOf(jsonObject), Object.class);
        modelMap.addAttribute("chartData", object);
//        System.out.println(object2);
        System.out.println(JsonUtils.gsonPretty(object));

        System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&&&& test YAML 2 &&&&&&&&&&&&&&&&&&&&&&&&&&");

        return "chart/index";
    }


}
