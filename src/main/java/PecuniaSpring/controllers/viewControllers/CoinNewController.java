package PecuniaSpring.controllers.viewControllers;

import PecuniaSpring.models.sqlClass.CountryByStatus;
import PecuniaSpring.models.sqlClass.GetCoinsByStatus;
import PecuniaSpring.services.coinServices.CoinServiceImpl;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import utils.JsonUtils;

import java.util.List;


@Controller
@AllArgsConstructor
public class CoinNewController {

    private CoinServiceImpl coinService;

    @GetMapping("/coin/newCoins")
    public String getIndex(ModelMap modelMap) {

        try {
            List<CountryByStatus> countryByStatusList = coinService.getCountryByStatus("NEW");
            modelMap.addAttribute("countryByStatusList", countryByStatusList);
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return "coin/newCoin/index";
    }

    @GetMapping("/coin/newCoin/country/coins/{countryId}")
    public String getCountryNotes(@PathVariable("countryId") Long countryId, ModelMap modelMap) {

        List<GetCoinsByStatus> getCoinsByStatusList = coinService.getCoinsByStatus("NEW", countryId);
        modelMap.addAttribute("newCoinsList", getCoinsByStatusList);
        System.out.println(JsonUtils.gsonPretty(getCoinsByStatusList));
        return "coin/newCoin/coins";
    }
}
