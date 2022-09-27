package PecuniaSpring.controllers.viewControllers;

import PecuniaSpring.controllers.dto.country.CountryGetCurrencyDto;
import PecuniaSpring.controllers.dto.country.CountryGetDto;
import PecuniaSpring.models.Country;
import PecuniaSpring.models.repositories.CountryRepository;
import PecuniaSpring.services.countryServices.CountryServiceImpl;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import utils.JsonUtils;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Controller
public class CurrencyController {

    private CountryServiceImpl countryService;
    private CountryRepository countryRepository;

    @GetMapping("/currency")
    public String getIndex(ModelMap modelMap) {


//        return "currency/index";
        return getSearch("", modelMap);
    }

    @GetMapping("/currency/list/{countryId}")
    public String getCountryCurrency(@PathVariable(value = "countryId") Long countryId, ModelMap modelMap) {
        System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
        System.out.println("Country Id - " + countryId);
        Country country = countryService.getCountryById(countryId);
        CountryGetCurrencyDto countryGetCurrencyDto = new ModelMapper().map(country, CountryGetCurrencyDto.class);
        System.out.println(JsonUtils.gsonPretty(countryGetCurrencyDto));
        System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
        modelMap.addAttribute("country", countryGetCurrencyDto);
        return "currency/list";
    }

    @PostMapping("/search")
    public String getSearch(@RequestParam(value = "keyword") String keyword, ModelMap modelMap) {
        if (keyword.isEmpty()) {
            System.out.println(" pole wyszukiwania jest puste");
            List<CountryGetDto> countryGetDtos = new ArrayList<>();
            List<Country> countries = countryService.getCountryByCountryEnAsc();
            for (Country country : countries) {
                countryGetDtos.add(new ModelMapper().map(country, CountryGetDto.class));
            }
            modelMap.addAttribute("countries", countryGetDtos);
            System.out.println("****************************************************");
            System.out.println("TU JESTEM");
            System.out.println(countryGetDtos.size());
//        System.out.println(JsonUtils.gsonPretty(countryGetDtos));
            System.out.println("****************************************************");
        }
        System.out.println(keyword);
        modelMap.addAttribute("keyword", keyword);
        List<Country> countries = countryService.searchCountry(keyword);
        List<CountryGetDto> countryGetDtos = new ArrayList<>();
        for (Country country : countries) {
            countryGetDtos.add(new ModelMapper().map(country, CountryGetDto.class));
        }
        modelMap.addAttribute("countries", countryGetDtos);
        System.out.println(JsonUtils.gsonPretty(countryGetDtos));
        return "currency/index";
    }
}
