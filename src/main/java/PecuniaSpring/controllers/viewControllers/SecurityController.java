package PecuniaSpring.controllers.viewControllers;

import PecuniaSpring.services.countryServices.CountryServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import utils.Search;

@Controller
@AllArgsConstructor
public class SecurityController {

    private CountryServiceImpl countryService;

    @GetMapping("/security")
    public String getIndex(ModelMap modelMap) {

        return getSearch("", modelMap);
    }

    @PostMapping("/security/search")
    public String getSearch(@RequestParam(value = "keyword") String keyword, ModelMap modelMap) {
        Search.searchCountry(keyword, modelMap, countryService);
//        System.out.println(JsonUtils.gsonPretty(countryGetDtos));
        return "security/index";
    }
}
