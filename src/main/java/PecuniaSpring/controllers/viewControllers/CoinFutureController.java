package PecuniaSpring.controllers.viewControllers;

import PecuniaSpring.models.repositories.CoinRepository;
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

import java.util.ArrayList;
import java.util.List;

@Controller
@AllArgsConstructor
public class CoinFutureController {

    private CoinRepository coinRepository;
    private CoinServiceImpl coinService;

    @GetMapping("/coin/future")
    public String getindex(ModelMap modelMap) {
        List<Object[]> objects;
        List<CountryByStatus> countryByStatusList = new ArrayList<>();
        objects = coinRepository.countryByStatus("FUTURE");
        try {
            System.out.println(JsonUtils.gsonPretty(objects));
            for (Object[] object : objects) {
                countryByStatusList.add(new ModelMapper().map(object[0], CountryByStatus.class));
            }

            modelMap.addAttribute("countryByStatusList", countryByStatusList);
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return "coin/future/index";
    }

    @GetMapping("/coin/future/country/coins/{countryId}")
    public String getCountryNotes(@PathVariable("countryId") Long countryId, ModelMap modelMap) {

        List<GetCoinsByStatus> getCoinsByStatusList = coinService.getCoinsByStatus("FUTURE", countryId);
        modelMap.addAttribute("futureCoinsList", getCoinsByStatusList);
        System.out.println(JsonUtils.gsonPretty(getCoinsByStatusList));
        return "coin/future/coins";
    }
}
