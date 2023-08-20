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
import org.springframework.web.bind.annotation.PostMapping;
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
        modelMap.addAttribute("typeChart","polarArea");
        modelMap.addAttribute("reportName", "my_report_continents_test");
//        modelMap.addAttribute("reportName", "my_report_note_currency_country");
//        modelMap.addAttribute("reportName", "my_report_object_status");
//        modelMap.addAttribute("parametr", "England");

        try {
            List<ReportMethod> reportMethods = chartService.reportMethodList(ChartRepository.class);
            System.out.println(JsonUtils.gsonPretty(reportMethods));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return "chart/index";
    }

    @PostMapping("/chart")
    public String postIndex(ModelMap modelMap) {

        return null;
    }

}
