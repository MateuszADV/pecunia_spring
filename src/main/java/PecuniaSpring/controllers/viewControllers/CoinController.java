package PecuniaSpring.controllers.viewControllers;

import PecuniaSpring.models.Country;
import PecuniaSpring.models.Currency;
import PecuniaSpring.models.dto.country.CountryDtoForm;
import PecuniaSpring.models.dto.currency.CurrencyDtoByNote;
import PecuniaSpring.services.countryServices.CountryServiceImpl;
import PecuniaSpring.services.currencyService.CurrencyServiceImpl;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import utils.JsonUtils;
import utils.Search;

import java.util.ArrayList;
import java.util.List;

@Controller
@AllArgsConstructor
public class CoinController {

    private CountryServiceImpl countryService;
    private CurrencyServiceImpl currencyService;


    @GetMapping("/coin")
    public String getIndex(ModelMap modelMap) {

        return getSearch("", modelMap);
    }

    @GetMapping("/coin/currency/{countryEn}")
    public String getNoteCurrency(@PathVariable String countryEn, ModelMap modelMap) {
        Country country = countryService.getCountyByCountryEn(countryEn);
        CountryDtoForm countryDto = new ModelMapper().map(country, CountryDtoForm.class);
        List<Currency> currencies = currencyService.getCurrencyByCountryByPattern(countryDto.getId(), "COIN");
        List<CurrencyDtoByNote> currencyDtoByNotes = new ArrayList<>();
        for (Currency currency : currencies) {
            currencyDtoByNotes.add(new ModelMapper().map(currency, CurrencyDtoByNote.class));
        }

        System.out.println("=======================START===========================");
        System.out.println(countryEn);
        System.out.println(JsonUtils.gsonPretty(countryDto));
        System.out.println("---------------------------------------------------------");
        System.out.println(currencyDtoByNotes.size());
        for (CurrencyDtoByNote currencyDtoByNote : currencyDtoByNotes) {
            System.out.println(currencyDtoByNote.getCurrencySeries());
        }
        System.out.println(JsonUtils.gsonPretty(currencyDtoByNotes));
        System.out.println("=======================STOP===========================");
        modelMap.addAttribute("currencies", currencyDtoByNotes);
        return "coin/currency";
    }


    @PostMapping("/coin/search")
    public String getSearch(@RequestParam(value = "keyword") String keyword, ModelMap modelMap) {
        Search.searchCountry(keyword, modelMap, countryService);
//        System.out.println(JsonUtils.gsonPretty(countryGetDtos));
        return "coin/index";
    }
}
