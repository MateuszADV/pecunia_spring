package PecuniaSpring.controllers.viewControllers;

import PecuniaSpring.models.repositories.CoinRepository;
import PecuniaSpring.models.sqlClass.CountryByStatus;
import PecuniaSpring.models.sqlClass.GetCoinsByStatus;
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
public class CoinForSellController {

    private CoinRepository coinRepository;

    @GetMapping("/coin/forSell")
    public String getindex(ModelMap modelMap) {
        List<Object[]> objects;
        List<CountryByStatus> countryByStatusList = new ArrayList<>();
        objects = coinRepository.countryByStatus("FOR SELL");
        try {
            System.out.println(JsonUtils.gsonPretty(objects));
            for (Object[] object : objects) {
                countryByStatusList.add(new ModelMapper().map(object[0], CountryByStatus.class));
            }

            modelMap.addAttribute("countryByStatusList", countryByStatusList);
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return "coin/forSell/index";
    }

    @GetMapping("/coin/forSell/country/coins/{countryId}")
    public String getCountryNotes(@PathVariable("countryId") Long countryId, ModelMap modelMap) {
        System.out.println("[[[[[[[[[[[[[[[[[[[[]]]]]]]]]]]]]]]]]]]]]]]]]]]C");
        System.out.println(countryId);
        System.out.println("[[[[[[[[[[[[[[[[[[[[]]]]]]]]]]]]]]]]]]]]]]]]]]]C");

        List<Object[]> objects;
        objects = coinRepository.getCoinsByStatus("FOR SELL", countryId);
        List<GetCoinsByStatus> getCoinsByStatusList = new ArrayList<>();
        for (Object[] object : objects) {
            getCoinsByStatusList.add(new ModelMapper().map(object[0],GetCoinsByStatus.class));
        }

        modelMap.addAttribute("forSellCoinsList", getCoinsByStatusList);
        System.out.println(JsonUtils.gsonPretty(getCoinsByStatusList));
        return "coin/forSell/coins";
    }
}