package PecuniaSpring.controllers.viewControllers;

import PecuniaSpring.models.sqlClass.CountryByStatus;
import PecuniaSpring.models.sqlClass.CurrencyByStatus;
import PecuniaSpring.security.config.UserCheckLoged;
import PecuniaSpring.services.coinServices.CoinServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import utils.JsonUtils;

import java.util.ArrayList;
import java.util.List;

@Controller
public class CoinCollectonController {

    private CoinServiceImpl coinService;
    private UserCheckLoged userCheckLoged;

    @Autowired
    public CoinCollectonController(CoinServiceImpl coinService, UserCheckLoged userCheckLoged) {
        this.coinService = coinService;
        this.userCheckLoged = userCheckLoged;
    }

    @GetMapping("/coin/collection")
    public String getIndex(ModelMap modelMap) {
        String role = userCheckLoged.UserCheckLoged().getAuthorities().toArray()[0].toString();

        List<CountryByStatus> countryByStatusList = new ArrayList<>();
        if (role == "ADMIN") {
            countryByStatusList = coinService.getCountryByStatus("KOLEKCJA", role);
        } else {
            countryByStatusList = coinService.getCountryByStatus("KOLEKCJA", role);
            System.out.println("Brak uprawnień to tu");
            modelMap.addAttribute("error", "Brak Uprawnień");
//            return "error";
        }
        modelMap.addAttribute("countryByStatusList", countryByStatusList);
        System.out.println(countryByStatusList.size());
        System.out.println(JsonUtils.gsonPretty(countryByStatusList));
        return "coin/collection/index";
    }

    @GetMapping("/coin/collection/currency")
    public String getCurrency(@RequestParam("selectCountryId") Long countryId, ModelMap modelMap) {
        String role = userCheckLoged.UserCheckLoged().getAuthorities().toArray()[0].toString();

        System.out.println(countryId);
//        List<Object[]> objects = noteRepository.currencyByStatus("KOLEKCJA", countryId);
        List<CurrencyByStatus> currencyByStatusList = new ArrayList<>();
        if (role == "ADMIN") {
            currencyByStatusList = coinService.getCurrencyByStatus(countryId, "KOLEKCJA", role);
        } else {
            currencyByStatusList = coinService.getCurrencyByStatus(countryId, "KOLEKCJA", role);
        }
        modelMap.addAttribute("currencyByStatusList", currencyByStatusList);
        System.out.println(JsonUtils.gsonPretty(currencyByStatusList));
        return "coin/collection/currency";
    }
}
