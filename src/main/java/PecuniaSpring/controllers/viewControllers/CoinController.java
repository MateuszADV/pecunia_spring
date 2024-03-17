package PecuniaSpring.controllers.viewControllers;

import PecuniaSpring.models.*;
import PecuniaSpring.models.dto.active.ActiveDtoSelect;
import PecuniaSpring.models.dto.bought.BoughtDto;
import PecuniaSpring.models.dto.coin.CoinDto;
import PecuniaSpring.models.dto.coin.CoinDtoByCurrency;
import PecuniaSpring.models.dto.coin.CoinFormDto;
import PecuniaSpring.models.dto.country.CountryDtoForm;
import PecuniaSpring.models.dto.currency.CurrencyDto;
import PecuniaSpring.models.dto.currency.CurrencyDtoByPattern;
import PecuniaSpring.models.dto.imageType.ImageTypeDtoSelect;
import PecuniaSpring.models.dto.making.MakingDtoSelect;
import PecuniaSpring.models.dto.quality.QualityDtoSelect;
import PecuniaSpring.models.dto.status.StatusDtoSelect;
import PecuniaSpring.models.other.variable.VariableForm;
import PecuniaSpring.services.activeService.ActiveServiceImpl;
import PecuniaSpring.services.boughtServices.BoughtServicesImpl;
import PecuniaSpring.services.coinServices.CoinServiceImpl;
import PecuniaSpring.services.countryServices.CountryServiceImpl;
import PecuniaSpring.services.currencyService.CurrencyServiceImpl;
import PecuniaSpring.services.imageTypeService.ImageTypeSeviceImpl;
import PecuniaSpring.services.makingServices.MakingServiceImpl;
import PecuniaSpring.services.quality.QualityServiceImpl;
import PecuniaSpring.services.statusService.StatusServiceImpl;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import utils.JsonUtils;
import utils.Search;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@AllArgsConstructor
public class CoinController {

    private CountryServiceImpl countryService;
    private CurrencyServiceImpl currencyService;
    private CoinServiceImpl coinService;
    private BoughtServicesImpl boughtServices;
    private ActiveServiceImpl activeService;
    private MakingServiceImpl makingService;
    private QualityServiceImpl qualityService;
    private StatusServiceImpl statusService;
    private ImageTypeSeviceImpl imageTypeSevice;

    Optional<Coin> coinTmp;


    @GetMapping("/coin")
    public String getIndex(ModelMap modelMap) {
        return getSearch("", modelMap);
    }

    @GetMapping("/coin/currency/{countryEn}")
    public String getCoinCurrency(@PathVariable String countryEn, ModelMap modelMap) {
        Country country = countryService.getCountyByCountryEn(countryEn);
        CountryDtoForm countryDto = new ModelMapper().map(country, CountryDtoForm.class);
        List<Currency> currencies = currencyService.getCurrencyByCountryByPattern(countryDto.getId(), "COIN");
        List<CurrencyDtoByPattern> currencyDtoByPatterns = new ArrayList<>();
        for (Currency currency : currencies) {
            currencyDtoByPatterns.add(new ModelMapper().map(currency, CurrencyDtoByPattern.class));
        }
        modelMap.addAttribute("currencies", currencyDtoByPatterns);
        return "coin/currency";
    }

    @PostMapping("/coin/search")
    public String getSearch(@RequestParam(value = "keyword") String keyword, ModelMap modelMap) {
        Search.searchCountry(keyword, modelMap, countryService);
        return "coin/index";
    }

    @GetMapping("/coin/coin_list")
    public String getCoinList(@RequestParam(value = "currencySeries") String currencySeries,
                              @RequestParam(value = "curId") Long  currencyId,
                              HttpServletRequest request,
                              ModelMap modelMap) {

        Currency currency = currencyService.getCurrencyById(currencyId);
        CurrencyDtoByPattern currencyDtoByPattern = new ModelMapper().map(currency, CurrencyDtoByPattern.class);
        List<Coin> coins = coinService.getCoinByCurrencyId(currencyId);
        List<CoinDtoByCurrency> coinDtoByCurrencies = new ArrayList();
        for (Coin coin : coins) {
            coinDtoByCurrencies.add(new ModelMapper().map(coin, CoinDtoByCurrency.class));
        }

        modelMap.addAttribute("currency", currencyDtoByPattern);
        modelMap.addAttribute("coins", coinDtoByCurrencies);
        return "/coin/coin_list";
    }

    @GetMapping("/coin/new")
    public String getNew(@RequestParam(value = "curId") Long currencyId,
                         ModelMap modelMap) {
        Currency currency = currencyService.getCurrencyById(currencyId);
        CurrencyDto currencyDto = new ModelMapper().map(currency, CurrencyDto.class);

        formVariable(modelMap, currency);

        CoinFormDto coinFormDto = new CoinFormDto();
        coinFormDto.setCurrencies(currencyDto);

        coinFormDto.setDateBuy(Date.valueOf(LocalDate.now()));
        coinFormDto.setPriceBuy(0.00);
        coinFormDto.setPriceSell(0.00);
        modelMap.addAttribute("coinForm", coinFormDto);
        return "coin/new";
    }

    @PostMapping("/coin/new")
    public String postNew(@ModelAttribute("coinForm")@Valid CoinFormDto coinForm, BindingResult result,
                          HttpServletRequest request,
                          ModelMap modelMap) {

        if (result.hasErrors()) {
            System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&ERROR&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&");
            System.out.println(result.toString());
            System.out.println(result.hasFieldErrors("dateBuy"));
            System.out.println(result.resolveMessageCodes("test błedu", "dateBuy").toString());

            if (result.hasFieldErrors("dateBuy")) {
                modelMap.addAttribute("errorDateBuy", "Podaj porawną datę");
            }
            System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&ERROR END&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&");
            Currency currency = currencyService.getCurrencyById(coinForm.getCurrencies().getId());
            formVariable(modelMap, currency);
            return "coin/new";
        }

        Currency currency = currencyService.getCurrencyById(coinForm.getCurrencies().getId());
        Coin coin = new ModelMapper().map(coinForm, Coin.class);
        coinService.saveCoin(coin);

//        return getCoinList(currency.getCurrencySeries(), currency.getId(), request, modelMap);
        return "redirect:/coin/coin_list/?currencySeries=" + currency.getCurrencySeries() + "&curId=" + currency.getId();
    }

    @GetMapping("/coin/edit/{coinId}")
    public String getEdit(@PathVariable Long coinId,
                          HttpServletRequest request,
                          HttpServletResponse response,
                          ModelMap modelMap) {
            coinTmp = Optional.ofNullable(coinService.getCoinById(coinId));
        CoinFormDto coinFormDto = new ModelMapper().map(coinTmp, CoinFormDto.class);
        modelMap.addAttribute("coinForm", coinFormDto);
        formVariable(modelMap, coinTmp.get().getCurrencies());
        return "coin/edit";
    }

    @PostMapping("/coin/edit")
    public String postEdit(@ModelAttribute ("coinForm")@Valid CoinFormDto coinForm, BindingResult result,
                           HttpServletRequest request,
                           ModelMap modelMap) {
        if (result.hasErrors()) {
            System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&ERROR&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&");
            System.out.println(result.toString());
            System.out.println(result.hasFieldErrors("dateBuy"));
            System.out.println(result.resolveMessageCodes("test błedu", "dateBuy").toString());

            if (result.hasFieldErrors("dateBuy")) {
                System.out.println(result.getFieldError("dateBuy").getDefaultMessage());
                System.out.println(result.getFieldError("dateBuy").getCode());
                modelMap.addAttribute("errorDateBuy", "Podaj porawną datę");
            }
            System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&ERROR END&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&");

            Currency currency = currencyService.getCurrencyById(coinForm.getCurrencies().getId());

            formVariable(modelMap, currency);
            return "coin/edit";
        }

        Currency currency = currencyService.getCurrencyById(coinForm.getCurrencies().getId());
        coinForm.setId(coinTmp.get().getId());
        coinForm.setCreated_at(coinTmp.get().getCreated_at());
        System.out.println(JsonUtils.gsonPretty(coinForm));
        System.out.println("++++++++++++++++++++++++++++++STOP+++++++++++++++++++++++++++++++");

        Coin coin = new ModelMapper().map(coinForm, Coin.class);

        coinService.saveCoin(coin);

//        return getcoinList(currency.getCurrencySeries(), currency.getId(), request, modelMap);
        return "redirect:/coin/coin_list/?currencySeries=" + currency.getCurrencySeries() + "&curId=" + currency.getId();
    }

    @GetMapping("/coin/delete/{noteId}")
    public String getDelete(@PathVariable Long coinId, HttpServletRequest request, ModelMap modelMap) {
        Coin coin = coinService.getCoinById(coinId);
        System.out.println("^^^^^^^^^^^^^^^^^^^^^^^BEGIN^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
        System.out.println(coin.getCurrencies().getCurrencySeries());
        System.out.println(coin.getCurrencies().getId());
        System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^END^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");

        coinService.deleteCoinById(coinId);

//        return getNoteList(note.getCurrencies().getCurrencySeries(), note.getCurrencies().getId(), request, modelMap);
        return "redirect:/coin/coin_list/?currencySeries=" + coin.getCurrencies().getCurrencySeries() + "&curId=" + coin.getCurrencies().getId();
    }

    @GetMapping("/coin/show/{coinId}")
    public String getShowCoin(@PathVariable Long coinId, ModelMap modelMap) {
        System.out.println(coinId);
        Coin coin = coinService.getCoinById(coinId);
        CoinDto coinDto = new ModelMapper().map(coin, CoinDto.class);
        System.out.println(JsonUtils.gsonPretty(coinDto));

        modelMap.addAttribute("coin", coinDto);
        modelMap.addAttribute("json", JsonUtils.gsonPretty(coinDto));
        return "/coin/show";
    }

    private void formVariable(ModelMap modelMap, Currency currency) {
        List<Currency> currenciesList = currencyService.getCurrencyByCountryByPattern(currency.getCountries().getId(), "COIN");
        List<CurrencyDto> currencyDtos = new ArrayList<>();
        for (Currency currency1 : currenciesList) {
            currencyDtos.add(new ModelMapper().map(currency1, CurrencyDto.class));
        }

        List<Bought> boughts = boughtServices.getAllBought();
        List<BoughtDto> boughtDtos = new ArrayList<>();
        for (Bought bought : boughts) {
            boughtDtos.add(new ModelMapper().map(bought, BoughtDto.class));
        }

        List<Active> actives = activeService.getAllActive();
        List<ActiveDtoSelect> activeDtoSelects = new ArrayList<>();
        for (Active active : actives) {
            activeDtoSelects.add(new ModelMapper().map(active, ActiveDtoSelect.class));
        }

        List<Making> makings = makingService.getAllMakings();
        List<MakingDtoSelect> makingDtoSelects = new ArrayList<>();
        for (Making making : makings) {
            makingDtoSelects.add(new ModelMapper().map(making, MakingDtoSelect.class));
        }

        List<Quality> qualities = qualityService.getAllQuality();
        List<QualityDtoSelect> qualityDtoSelects = new ArrayList<>();
        for (Quality quality : qualities) {
            qualityDtoSelects.add(new ModelMapper().map(quality, QualityDtoSelect.class));
        }

        List<Status> statuses = statusService.getAllStatuses();
        List<StatusDtoSelect> statusDtoSelects = new ArrayList<>();
        for (Status status : statuses) {
            statusDtoSelects.add(new ModelMapper().map(status, StatusDtoSelect.class));
        }

        List<ImageType> imageTypes = imageTypeSevice.getAllImageTypes();
        List<ImageTypeDtoSelect> imageTypeDtoSelects = new ArrayList<>();
        for (ImageType imageType : imageTypes) {
            imageTypeDtoSelects.add(new ModelMapper().map(imageType, ImageTypeDtoSelect.class));
        }

        VariableForm.variableToSelect(modelMap, currencyDtos, boughtDtos, activeDtoSelects, makingDtoSelects, qualityDtoSelects, statusDtoSelects, imageTypeDtoSelects);
    }
}
