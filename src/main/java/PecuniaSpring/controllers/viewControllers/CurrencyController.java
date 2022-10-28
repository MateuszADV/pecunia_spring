package PecuniaSpring.controllers.viewControllers;

import PecuniaSpring.models.dto.active.ActiveDtoCurrency;
import PecuniaSpring.models.dto.country.CountryGetCurrencyDto;
import PecuniaSpring.models.dto.country.CountryGetDto;
import PecuniaSpring.models.dto.currency.CurrencyActiveDto;
import PecuniaSpring.models.Active;
import PecuniaSpring.models.Country;
import PecuniaSpring.models.Currency;
import PecuniaSpring.models.repositories.CurrencyRepository;
import PecuniaSpring.services.activeService.ActiveServiceImpl;
import PecuniaSpring.services.countryServices.CountryServiceImpl;
import PecuniaSpring.services.currencyService.CurrencyServiceImpl;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import utils.JsonUtils;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Controller
public class CurrencyController {

    private CurrencyRepository currencyRepository;
    private CurrencyServiceImpl currencyService;
    private CountryServiceImpl countryService;
    private ActiveServiceImpl activeService;

    private Optional<Currency> currencyTemp;
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
//        System.out.println(JsonUtils.gsonPretty(countryGetDtos));
        return "currency/index";
    }

    @GetMapping("/currency/new")
    public String getNew(@RequestParam("countryId") Long countryId,  ModelMap modelMap) {
        System.out.println("===================Country ID===================");
        Country country = countryService.getCountryById(countryId);
        CountryGetDto countryGetDto = new ModelMapper().map(country, CountryGetDto.class);
        CurrencyActiveDto currencyActiveDto = new CurrencyActiveDto();
        currencyActiveDto.setCountries(countryGetDto);
        System.out.println(JsonUtils.gsonPretty(currencyActiveDto));
        System.out.println(currencyActiveDto.getActives());
        System.out.println(countryId);

        List<Active> actives = activeService.getAllActive();
        List<ActiveDtoCurrency> activeDtoCurrencies = new ArrayList<>();
        for (Active active : actives) {
            activeDtoCurrencies.add(new ModelMapper().map(active, ActiveDtoCurrency.class));
        }

        modelMap.addAttribute("actives", activeDtoCurrencies);
        modelMap.addAttribute("currencyForm", currencyActiveDto);
        System.out.println("================================================");
        return "currency/new";
    }

    @PostMapping("currency/new")
    public String postNew(@ModelAttribute("currencyForm") @Valid CurrencyActiveDto currencyForm,
                          BindingResult result,
                          ModelMap modelMap) {

        if (result.hasErrors()) {
            System.out.println(result.toString());
            System.out.println(JsonUtils.gsonPretty(currencyForm));

            List<Active> actives = activeService.getAllActive();
            List<ActiveDtoCurrency> activeDtoCurrencies = new ArrayList<>();
            for (Active active : actives) {
                activeDtoCurrencies.add(new ModelMapper().map(active, ActiveDtoCurrency.class));
            }

            modelMap.addAttribute("actives", activeDtoCurrencies);
            modelMap.addAttribute("currencyForm", currencyForm);

            return "currency/new";
        }
        Active active = activeService.getActiveById(currencyForm.getActives().getId());
        System.out.println(currencyForm);
        System.out.println(JsonUtils.gsonPretty(currencyForm));
        Currency currency = new ModelMapper().map(currencyForm, Currency.class);
        currency.setActive(active.getActiveCod());
        currencyService.saveCurrency(currency);
        return getSearch("", modelMap);
    }

    @GetMapping("currency/edit/{currencyId}")
    public String postEdit(@PathVariable Long currencyId,
                          ModelMap modelMap) {
        currencyTemp = currencyRepository.findById(currencyId);
        CurrencyActiveDto currencyActiveDto = new ModelMapper().map(currencyTemp, CurrencyActiveDto.class);
        List<Active> actives = activeService.getAllActive();
        List<ActiveDtoCurrency> activeDtoCurrencies = new ArrayList<>();
        for (Active active : actives) {
            activeDtoCurrencies.add(new ModelMapper().map(active, ActiveDtoCurrency.class));
        }

        modelMap.addAttribute("actives", activeDtoCurrencies);
        modelMap.addAttribute("currencyForm", currencyActiveDto);
        System.out.println("+++++++++++++++++Strat Currency Edit++++++++++++++");
        System.out.println(currencyId);
        System.out.println(JsonUtils.gsonPretty(currencyActiveDto));
        return "currency/edit";
    }

    @PostMapping("currency/edit")
    public String postEdit(@ModelAttribute("currencyForm") @Valid CurrencyActiveDto currencyForm,
                          BindingResult result,
                          ModelMap modelMap) {

        if (result.hasErrors()) {
            System.out.println(JsonUtils.gsonPretty(currencyForm));

            List<Active> actives = activeService.getAllActive();
            List<ActiveDtoCurrency> activeDtoCurrencies = new ArrayList<>();
            for (Active active : actives) {
                activeDtoCurrencies.add(new ModelMapper().map(active, ActiveDtoCurrency.class));
            }

            modelMap.addAttribute("actives", activeDtoCurrencies);
            modelMap.addAttribute("currencyForm", currencyForm);

            return "currency/edit";
        }
        Active active = activeService.getActiveById(currencyForm.getActives().getId());
        System.out.println(JsonUtils.gsonPretty(currencyForm));
        currencyForm.setId(currencyTemp.get().getId());
        currencyForm.setCreated_at(currencyTemp.get().getCreated_at());
        currencyForm.setActive(active.getActiveCod());
        Currency currency = new ModelMapper().map(currencyForm, Currency.class);
        currencyService.saveCurrency(currency);
        return getSearch("", modelMap);
    }

    @GetMapping("/currency/show/{currencyId}")
    public String getShow(@PathVariable Long currencyId, ModelMap modelMap) {
        Currency currency = currencyService.getCurrencyById(currencyId);
        CurrencyActiveDto currencyActiveDto = new ModelMapper().map(currency, CurrencyActiveDto.class);
        System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++");
        System.out.println(currencyId);
        System.out.println(JsonUtils.gsonPretty(currencyActiveDto));

        modelMap.addAttribute("currency", currencyActiveDto);
        modelMap.addAttribute("json", JsonUtils.gsonPretty(currencyActiveDto));
        return "currency/show";
    }
}
