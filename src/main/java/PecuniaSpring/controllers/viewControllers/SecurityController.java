package PecuniaSpring.controllers.viewControllers;

import PecuniaSpring.models.Country;
import PecuniaSpring.models.Currency;
import PecuniaSpring.models.Security;
import PecuniaSpring.models.dto.country.CountryDtoForm;
import PecuniaSpring.models.dto.currency.CurrencyDto;
import PecuniaSpring.models.dto.currency.CurrencyDtoByPattern;
import PecuniaSpring.models.dto.security.SecurityDto;
import PecuniaSpring.services.countryServices.CountryServiceImpl;
import PecuniaSpring.services.currencyService.CurrencyServiceImpl;
import PecuniaSpring.services.securityService.SecurityServiceImpl;
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

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
@AllArgsConstructor
public class SecurityController {

    private CountryServiceImpl countryService;
    private CurrencyServiceImpl currencyService;
    private SecurityServiceImpl securityService;

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

    @GetMapping("/security/security_list")
    public String getNoteList(@RequestParam(value = "currencySeries") String currencySeries,
                              @RequestParam(value = "curId") Long  currencyId,
                              HttpServletRequest request,
                              ModelMap modelMap) {

        System.out.println("=======================START003++++++++++++++++++++++++++");
        System.out.println(request.getPathInfo());
        Currency currency = currencyService.getCurrencyById(currencyId);
        CurrencyDto currencyDto = new ModelMapper().map(currency, CurrencyDto.class);
        List<Security> securities = securityService.getSecurityByCurrencyId(currencyId);
        List<SecurityDto> securityDtos = new ArrayList();
        for (Security security : securities) {
            securityDtos.add(new ModelMapper().map(security, SecurityDto.class));
        }

        System.out.println(securities.size());
        System.out.println(JsonUtils.gsonPretty(securityDtos));

        modelMap.addAttribute("currency", currencyDto);
        modelMap.addAttribute("securities", securityDtos);
        return "/security/security_list";
    }
}
