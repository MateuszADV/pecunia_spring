package PecuniaSpring.controllers.viewControllers;

import PecuniaSpring.models.Pattern;
import PecuniaSpring.models.dto.active.ActiveDtoSelect;

import PecuniaSpring.models.dto.country.CountryGetCurrencyDto;
import PecuniaSpring.models.dto.country.CountryGetDto;
import PecuniaSpring.models.dto.currency.CurrencyDto;
import PecuniaSpring.models.dto.currency.CurrencyDtoForm;
import PecuniaSpring.models.Active;
import PecuniaSpring.models.Country;
import PecuniaSpring.models.Currency;
import PecuniaSpring.models.dto.pattern.PatternDtoCurrency;
import PecuniaSpring.models.repositories.CurrencyRepository;
import PecuniaSpring.services.activeService.ActiveServiceImpl;
import PecuniaSpring.services.countryServices.CountryServiceImpl;
import PecuniaSpring.services.currencyService.CurrencyServiceImpl;
import PecuniaSpring.services.patternService.PatternServiceImpl;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import utils.JsonUtils;
import utils.Search;

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
    private PatternServiceImpl patternService;

    private Optional<Currency> currencyTemp;
    @GetMapping("/currency")
    public String getIndex(ModelMap modelMap) {


//        return "currency/index";
        return getSearch("", modelMap);
    }

    @GetMapping("/currency/list/{countryId}")
    public String getCountryCurrency(@PathVariable(value = "countryId") Long countryId, ModelMap modelMap) {
        System.out.println("%%%%%%%%%%%%%%%%%%%%%%% START %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
        System.out.println("Country Id - " + countryId);
        Country country = countryService.getCountryById(countryId);

        List<CurrencyDto> currencyDtos = new ArrayList<>();
        for (Currency currency : country.getCurrencies()) {
            currencyDtos.add(new ModelMapper().map(currency, CurrencyDto.class));
        }
//        System.out.println(JsonUtils.gsonPretty(currencyDtos));
//        System.out.println(country.getCurrencies().size());

        //TODO Do sprawdzenia to mapowanie
        CountryGetCurrencyDto countryGetCurrencyDto = new ModelMapper().map(country, CountryGetCurrencyDto.class);

        countryGetCurrencyDto.setCurrencyDtos(currencyDtos);
        System.out.println(countryGetCurrencyDto.getCurrencyDtos().size());
        System.out.println(JsonUtils.gsonPretty(countryGetCurrencyDto));
        System.out.println("%%%%%%%%%%%%%%%%%%%%%%% END %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
        modelMap.addAttribute("country", countryGetCurrencyDto);
        return "currency/list";
    }

    @PostMapping("/currency/search")
    public String getSearch(@RequestParam(value = "keyword") String keyword, ModelMap modelMap) {
        Search.searchCountry(keyword, modelMap, countryService);
//        System.out.println(JsonUtils.gsonPretty(countryGetDtos));
        return "currency/index";
    }

    @GetMapping("/currency/new")
    public String getNew(@RequestParam("countryId") Long countryId,  ModelMap modelMap) {
        System.out.println("===================Country ID===================");
        Country country = countryService.getCountryById(countryId);
        CountryGetDto countryGetDto = new ModelMapper().map(country, CountryGetDto.class);
        CurrencyDtoForm currencyDtoForm = new CurrencyDtoForm();
        currencyDtoForm.setCountries(countryGetDto);
        System.out.println(JsonUtils.gsonPretty(currencyDtoForm));

        currencyParameters(currencyDtoForm, modelMap);
        System.out.println("================================================");
        return "currency/new";
    }

    @PostMapping("currency/new")
    public String postNew(@ModelAttribute("currencyForm") @Valid CurrencyDtoForm currencyForm,
                          BindingResult result,
                          ModelMap modelMap) {
        if (result.hasErrors()) {
            System.out.println(result.toString());
            System.out.println(JsonUtils.gsonPretty(currencyForm));
            currencyParameters(currencyForm, modelMap);
            return "currency/new";
        }

        System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%TEST%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
        Active active = activeService.getActiveById(currencyForm.getActives().getId());
        Pattern pattern = patternService.getPatternById(currencyForm.getPatterns().getId());
        currencyForm.setActive(active.getActiveCod());
        currencyForm.setPattern(pattern.getPattern());
        System.out.println(currencyForm);
        System.out.println(JsonUtils.gsonPretty(currencyForm));

        Currency currency = new ModelMapper().map(currencyForm, Currency.class);

        currencyService.saveCurrency(currency);
        return getCountryCurrency(currencyForm.getCountries().getId(), modelMap);

//        return getSearch("", modelMap);
    }

    private void currencyParameters(@ModelAttribute("currencyForm") @Valid CurrencyDtoForm currencyForm, ModelMap modelMap) {
        List<Active> actives = activeService.getAllActive();
        List<ActiveDtoSelect> activeDtoCurrencies = new ArrayList<>();
        for (Active active : actives) {
            activeDtoCurrencies.add(new ModelMapper().map(active, ActiveDtoSelect.class));
        }

        List<Pattern> patterns = patternService.getAllPattern();
        List<PatternDtoCurrency> patternDtoCurrencies = new ArrayList<>();
        for (Pattern pattern : patterns) {
            patternDtoCurrencies.add(new ModelMapper().map(pattern, PatternDtoCurrency.class));
        }

        modelMap.addAttribute("actives", activeDtoCurrencies);
        modelMap.addAttribute("patterns", patternDtoCurrencies);
        modelMap.addAttribute("currencyForm", currencyForm);
    }

    @GetMapping("currency/edit/{currencyId}")
    public String postEdit(@PathVariable Long currencyId,
                          ModelMap modelMap) {
        currencyTemp = currencyRepository.findById(currencyId);
        CurrencyDtoForm currencyDtoForm = new ModelMapper().map(currencyTemp, CurrencyDtoForm.class);
        System.out.println("*****************************START************************************");
        System.out.println(JsonUtils.gsonPretty(currencyDtoForm));
        System.out.println("*****************************STOP************************************");

        List<Active> actives = activeService.getAllActive();
        List<ActiveDtoSelect> activeDtoCurrencies = new ArrayList<>();
        for (Active active : actives) {
            activeDtoCurrencies.add(new ModelMapper().map(active, ActiveDtoSelect.class));
        }

        List<Pattern> patterns = patternService.getAllPattern();
        List<PatternDtoCurrency> patternDtoCurrencies = new ArrayList<>();
        for (Pattern pattern : patterns) {
            patternDtoCurrencies.add(new ModelMapper().map(pattern, PatternDtoCurrency.class));
        }

        modelMap.addAttribute("actives", activeDtoCurrencies);
        modelMap.addAttribute("patterns", patternDtoCurrencies);
        modelMap.addAttribute("currencyForm", currencyDtoForm);
        System.out.println("+++++++++++++++++Strat Currency Edit++++++++++++++");
        System.out.println(currencyId);
        System.out.println(JsonUtils.gsonPretty(currencyDtoForm));
        return "currency/edit";
    }

    @PostMapping("currency/edit")
    public String postEdit(@ModelAttribute("currencyForm") @Valid CurrencyDtoForm currencyForm,
                          BindingResult result,
                          ModelMap modelMap) {

        if (result.hasErrors()) {
            System.out.println(JsonUtils.gsonPretty(currencyForm));

            List<Active> actives = activeService.getAllActive();
            List<ActiveDtoSelect> activeDtoCurrencies = new ArrayList<>();
            for (Active active : actives) {
                activeDtoCurrencies.add(new ModelMapper().map(active, ActiveDtoSelect.class));
            }

            List<Pattern> patterns = patternService.getAllPattern();
            List<PatternDtoCurrency> patternDtoCurrencies = new ArrayList<>();
            for (Pattern pattern : patterns) {
                patternDtoCurrencies.add(new ModelMapper().map(pattern, PatternDtoCurrency.class));
            }

            modelMap.addAttribute("actives", activeDtoCurrencies);
            modelMap.addAttribute("patterns", patternDtoCurrencies);
            modelMap.addAttribute("currencyForm", currencyForm);

            return "currency/edit";
        }

        Pattern pattern = patternService.getPatternById(currencyForm.getPatterns().getId());
        Active active = activeService.getActiveById(currencyForm.getActives().getId());
        currencyForm.setId(currencyTemp.get().getId());
        currencyForm.setCreated_at(currencyTemp.get().getCreated_at());
        currencyForm.setPattern(pattern.getPattern());
        currencyForm.setActive(active.getActiveCod());
        Currency currency = new ModelMapper().map(currencyForm, Currency.class);
        currencyService.saveCurrency(currency);

        System.out.println("%%%%%%%%%%%%%%%%%%%%%%START EDIT%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
        System.out.println(JsonUtils.gsonPretty(currencyForm));
        System.out.println("%%%%%%%%%%%%%%%%%%%%%%STOP EDIT%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");

        return getCountryCurrency(currencyForm.getCountries().getId(), modelMap);
    }

    @GetMapping("/currency/show/{currencyId}")
    public String getShow(@PathVariable Long currencyId, ModelMap modelMap) {
        Currency currency = currencyService.getCurrencyById(currencyId);
        CurrencyDto currencyDto = new ModelMapper().map(currency, CurrencyDto.class);
        System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++");
        System.out.println(currencyId);
        System.out.println(JsonUtils.gsonPretty(currencyDto));

        modelMap.addAttribute("currency", currencyDto);
        modelMap.addAttribute("json", JsonUtils.gsonPretty(currencyDto));
        return "currency/show";
    }

    @GetMapping("/currency/delete/{currencyId}/{countryId}")
    public String getDelete(@PathVariable Long currencyId, @PathVariable Long countryId, ModelMap modelMap) {
        try {
            currencyService.deleteCurrencyById(currencyId);
        }catch (Exception e) {
            System.out.println(e.getMessage());
            modelMap.addAttribute("error", e.getMessage());
//            return "error";
        }
        return "redirect:/currency/list/" + countryId;
    }
}
