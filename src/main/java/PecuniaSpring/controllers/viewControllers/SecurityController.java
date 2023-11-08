package PecuniaSpring.controllers.viewControllers;

import PecuniaSpring.models.Country;
import PecuniaSpring.models.Currency;
import PecuniaSpring.models.dto.country.CountryDtoForm;
import PecuniaSpring.models.dto.currency.CurrencyDtoByPattern;
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
public class SecurityController {

    private CountryServiceImpl countryService;
    private CurrencyServiceImpl currencyService;

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

    @GetMapping("/security/currency/{countryEn}")
    public String getSecurityCurrency(@PathVariable String countryEn, ModelMap modelMap) {
        System.out.println("================START2==========================");
        System.out.println(countryEn);
//        Country country = countryService.getCountyByCountryEn(countryEn);
//        CountryDtoForm countryDto = new ModelMapper().map(country, CountryDtoForm.class);
//        List<Currency> currencies = currencyService.getCurrencyByCountryByPattern(countryDto.getId(), "BOND");
        List<Currency> currencies = currencyService.getCurrencyByCountryByPattern(countryEn, "SECURITY");
        List<CurrencyDtoByPattern> currencyDtoByPatterns = new ArrayList<>();
        for (Currency currency : currencies) {
            currencyDtoByPatterns.add(new ModelMapper().map(currency, CurrencyDtoByPattern.class));
        }

//        System.out.println("=======================START===========================");
//        System.out.println(countryEn);
//        System.out.println(JsonUtils.gsonPretty(countryDto));
//        System.out.println("---------------------------------------------------------");
        System.out.println(currencyDtoByPatterns.size());
        for (CurrencyDtoByPattern currencyDtoByPattern : currencyDtoByPatterns) {
            System.out.println(currencyDtoByPattern.getCurrencySeries());
        }
        System.out.println(JsonUtils.gsonPretty(currencyDtoByPatterns));
        System.out.println("=======================STOP===========================");
        modelMap.addAttribute("currencies", currencyDtoByPatterns);
        return "security/currency";
    }
}
