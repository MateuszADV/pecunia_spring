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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
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
    public String getIndex(ModelMap modelMap) throws FileNotFoundException {
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

        System.out.println(data.toString());
        System.out.println(labels);

        System.out.println("*******************TES YAML FILE*********************************");

        Yaml yaml = new Yaml();
        InputStream inputStream = this.getClass()
                .getClassLoader()
                .getResourceAsStream("static/reportsChart/reports/my_report.yml");
        Map<String, Object> obj = yaml.load(inputStream);
        System.out.println(inputStream.toString());
        System.out.println(obj);

//        System.out.println(JsonUtils.gsonPretty(obj));
        System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        JSONObject jsonObject = new JSONObject(obj);

        jsonObject.getJSONObject("chart").put("labels",labels);
        jsonObject.getJSONObject("chart").getJSONObject("datasets").put("data", data);

        Object object2 = new Gson().fromJson(String.valueOf(jsonObject), Object.class);
        modelMap.addAttribute("chartData", object2);
        System.out.println(object2);
//        System.out.println(JsonUtils.gsonPretty(jsonObject));
        return "chart/index";
    }
}
