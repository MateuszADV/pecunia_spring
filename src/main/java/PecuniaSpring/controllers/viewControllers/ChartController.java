package PecuniaSpring.controllers.viewControllers;

import PecuniaSpring.chartClass.ReportMethod;
import PecuniaSpring.models.repositories.ChartRepository;
import PecuniaSpring.models.repositories.CountryRepository;
import PecuniaSpring.services.chartServices.ChartServiceImpl;
import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
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
    private ChartServiceImpl chartService;
    public ChartRepository chartRepository;

    @Autowired
    public ChartController(CountryRepository countryRepository, ChartServiceImpl chartService, ChartRepository chartRepository) {
        this.countryRepository = countryRepository;
        this.chartService = chartService;
        this.chartRepository = chartRepository;
    }

    @GetMapping("/chart")
    public String getIndex(ModelMap modelMap) throws IOException {
//        List<Object[]> objects = countryRepository.reportCountCountry();
//        List<String> labels = new ArrayList<>();
//        List<Integer> data = new ArrayList<>();
//        for (Object[] object : objects) {
//            labels.add(object[0].toString());
//            data.add(Integer.valueOf(object[1].toString()));
//        }

//        Yaml yaml = new Yaml();
//        InputStream inputStream = this.getClass()
//                .getClassLoader()
//                .getResourceAsStream("static/reportsChart/reports/my_report_continents_test.yml");
//        Map<String, Object> obj = yaml.load(inputStream);
//
//        System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
//        JSONObject jsonObject = new JSONObject(obj);
//
//        /**
//         * Dodawanie danych z bazy do wyświetlenia na wykresie
//         **/
//        jsonObject.getJSONObject("chart").put("labels",labels);
//        jsonObject.getJSONObject("chart").getJSONObject("datasets").put("data", data);
//
//        Object object = new Gson().fromJson(String.valueOf(jsonObject), Object.class);
//        modelMap.addAttribute("chartData", object);
//        System.out.println(JsonUtils.gsonPretty(object));
//
//        System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&&&& test YAML 2 &&&&&&&&&&&&&&&&&&&&&&&&&&");

//        modelMap.addAttribute("reportName", "my_report_continents_test");
//        modelMap.addAttribute("reportName", "my_report_continents_test");
        modelMap.addAttribute("reportName", "my_report_note_currency_country");
        modelMap.addAttribute("parametr", "England");

        try {
            List<ReportMethod> reportMethods = chartService.reportMethodList(ChartRepository.class);
            System.out.println(JsonUtils.gsonPretty(reportMethods));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
//        System.out.println(reportMethods);
//        System.out.println(JsonUtils.gsonPretty(chartService.reportMethodList(ChartRepository.class)));
        return "chart/index";
    }


}
