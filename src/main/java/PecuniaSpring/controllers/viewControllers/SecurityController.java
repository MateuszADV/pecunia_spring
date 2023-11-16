package PecuniaSpring.controllers.viewControllers;

import PecuniaSpring.models.*;
import PecuniaSpring.models.dto.active.ActiveDtoSelect;
import PecuniaSpring.models.dto.bought.BoughtDto;
import PecuniaSpring.models.dto.currency.CurrencyDto;
import PecuniaSpring.models.dto.currency.CurrencyDtoByPattern;
import PecuniaSpring.models.dto.imageType.ImageTypeDtoSelect;
import PecuniaSpring.models.dto.making.MakingDtoSelect;
import PecuniaSpring.models.dto.quality.QualityDtoSelect;
import PecuniaSpring.models.dto.security.SecurityDto;
import PecuniaSpring.models.dto.security.SecurityFormDto;
import PecuniaSpring.models.dto.status.StatusDtoSelect;
import PecuniaSpring.services.activeService.ActiveServiceImpl;
import PecuniaSpring.services.boughtServices.BoughtServicesImpl;
import PecuniaSpring.services.countryServices.CountryServiceImpl;
import PecuniaSpring.services.currencyService.CurrencyServiceImpl;
import PecuniaSpring.services.imageTypeService.ImageTypeSeviceImpl;
import PecuniaSpring.services.makingServices.MakingServiceImpl;
import PecuniaSpring.services.quality.QualityServiceImpl;
import PecuniaSpring.services.securityService.SecurityServiceImpl;
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
import javax.validation.Valid;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@AllArgsConstructor
public class SecurityController {

    private CountryServiceImpl countryService;
    private CurrencyServiceImpl currencyService;
    private SecurityServiceImpl securityService;
    private BoughtServicesImpl boughtServices;
    private ActiveServiceImpl activeService;
    private MakingServiceImpl makingService;
    private QualityServiceImpl qualityService;
    private StatusServiceImpl statusService;
    private ImageTypeSeviceImpl imageTypeSevice;

    @GetMapping("/security")
    public String getIndex(ModelMap modelMap) {
        return getSearch("", modelMap);
    }

    @PostMapping("/security/search")
    public String getSearch(@RequestParam(value = "keyword") String keyword, ModelMap modelMap) {
        Search.searchCountry(keyword, modelMap, countryService);
        return "security/index";
    }

    @GetMapping("/security/currency/{countryEn}")
    public String getSecurityCurrency(@PathVariable String countryEn, ModelMap modelMap) {
        List<Currency> currencies = currencyService.getCurrencyByCountryByPattern(countryEn, "SECURITY");
        List<CurrencyDtoByPattern> currencyDtoByPatterns = new ArrayList<>();
        for (Currency currency : currencies) {
            currencyDtoByPatterns.add(new ModelMapper().map(currency, CurrencyDtoByPattern.class));
        }
        for (CurrencyDtoByPattern currencyDtoByPattern : currencyDtoByPatterns) {
            System.out.println(currencyDtoByPattern.getCurrencySeries());
        }
        modelMap.addAttribute("currencies", currencyDtoByPatterns);
        return "security/currency";
    }

    @GetMapping("/security/security_list")
    public String getNoteList(@RequestParam(value = "currencySeries") String currencySeries,
                              @RequestParam(value = "curId") Long  currencyId,
                              HttpServletRequest request,
                              ModelMap modelMap) {
        Currency currency = currencyService.getCurrencyById(currencyId);
        CurrencyDto currencyDto = new ModelMapper().map(currency, CurrencyDto.class);
        List<Security> securities = securityService.getSecurityByCurrencyId(currencyId);
        List<SecurityDto> securityDtos = new ArrayList();
        for (Security security : securities) {
            securityDtos.add(new ModelMapper().map(security, SecurityDto.class));
        }
        modelMap.addAttribute("currency", currencyDto);
        modelMap.addAttribute("securities", securityDtos);
        return "/security/security_list";
    }

    @GetMapping("/security/new")
    public String getNew(@RequestParam(value = "curId") Long currencyId,
                         ModelMap modelMap) {
        Currency currency = currencyService.getCurrencyById(currencyId);
        CurrencyDto currencyDto = new ModelMapper().map(currency, CurrencyDto.class);

        formVariable(modelMap, currency);

        SecurityFormDto securityFormDto = new SecurityFormDto();
        securityFormDto.setCurrencies(currencyDto);

        securityFormDto.setDateBuy(Date.valueOf(LocalDate.now()));
        securityFormDto.setPriceBuy(0.00);
        securityFormDto.setPriceSell(0.00);
        modelMap.addAttribute("securityForm", securityFormDto);
        return "security/new";
    }

    @PostMapping("/security/new")
    public String postNew(@ModelAttribute("securityForm")@Valid SecurityFormDto securityForm, BindingResult result,
                          HttpServletRequest request,
                          ModelMap modelMap) {

        if (result.hasErrors()) {
            System.out.println(result.toString());
            System.out.println(result.hasFieldErrors("dateBuy"));
            System.out.println(result.resolveMessageCodes("test błedu", "dateBuy").toString());

            if (result.hasFieldErrors("dateBuy")) {
                System.out.println(result.getFieldError("dateBuy").getDefaultMessage());
                System.out.println(result.getFieldError("dateBuy").getCode());
                modelMap.addAttribute("errorDateBuy", "Podaj porawną datę");
            }
            Currency currency = currencyService.getCurrencyById(securityForm.getCurrencies().getId());
            formVariable(modelMap, currency);
            return "security/new";
        }
        Currency currency = currencyService.getCurrencyById(securityForm.getCurrencies().getId());
        Security security = new ModelMapper().map(securityForm, Security.class);
        securityService.saveSecurity(security);
        return "redirect:/security/security_list/?currencySeries=" + currency.getCurrencySeries() + "&curId=" + currency.getId();
    }

    private void formVariable(ModelMap modelMap, Currency currency) {
        List<Currency> currenciesList = currencyService.getCurrencyByCountryByPattern(currency.getCountries().getCountryEn(), "SECURITY");
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

        modelMap.addAttribute("currencies", currencyDtos);
        modelMap.addAttribute("boughts", boughtDtos);
        modelMap.addAttribute("actives", activeDtoSelects);
        modelMap.addAttribute("makings", makingDtoSelects);
        modelMap.addAttribute("qualities", qualityDtoSelects);
        modelMap.addAttribute("statuses", statusDtoSelects);
        modelMap.addAttribute("imageTypes", imageTypeDtoSelects);
        modelMap.addAttribute("standartDate", Date.valueOf(LocalDate.now()));
    }

    @GetMapping("/security/show/{securityId}")
    public String getShowSecurity(@PathVariable Long securityId, ModelMap modelMap) {
        System.out.println(securityId);
        Security security = securityService.getSecurityById(securityId);
        SecurityDto securityDto = new ModelMapper().map(security, SecurityDto.class);
        System.out.println(JsonUtils.gsonPretty(securityDto));

        modelMap.addAttribute("security", securityDto);
        return "/security/show";
    }

    @GetMapping("/security/edit/{securityId}")
    public String getEdit(@PathVariable Long securityId, ModelMap modelMap) {
        Optional<Security> security;
        security = Optional.ofNullable(securityService.getSecurityById(securityId));
        SecurityFormDto securityFormDto = new ModelMapper().map(security, SecurityFormDto.class);
        System.out.println(JsonUtils.gsonPretty(securityFormDto));
        modelMap.addAttribute("securityForm", securityFormDto);
        formVariable(modelMap, security.get().getCurrencies());
        return "security/edit";
    }

    @PostMapping("/security/edit")
    public String postEdit(@ModelAttribute ("securityForm")@Valid SecurityFormDto securityForm, BindingResult result,
                           HttpServletRequest request,
                           ModelMap modelMap) {
        if (result.hasErrors()) {
            System.out.println(result.toString());
            System.out.println(result.hasFieldErrors("dateBuy"));
            System.out.println(result.resolveMessageCodes("test błedu", "dateBuy").toString());
            if (result.hasFieldErrors("dateBuy")) {
                System.out.println(result.getFieldError("dateBuy").getDefaultMessage());
                System.out.println(result.getFieldError("dateBuy").getCode());
                modelMap.addAttribute("errorDateBuy", "Podaj porawną datę");
            }

            Currency currency = currencyService.getCurrencyById(securityForm.getCurrencies().getId());

            formVariable(modelMap, currency);
            return "security/edit";
        }
        Currency currency = currencyService.getCurrencyById(securityForm.getCurrencies().getId());
        Security security = new ModelMapper().map(securityForm, Security.class);
        securityService.saveSecurity(security);
        return "redirect:/security/security_list/?currencySeries=" + currency.getCurrencySeries() + "&curId=" + currency.getId();
    }

    @GetMapping("/security/delete/{securityId}")
    public String getDelete(@PathVariable Long securityId, HttpServletRequest request, ModelMap modelMap) {
        Security security = securityService.getSecurityById(securityId);
        securityService.deleteSecurityById(securityId);
        return "redirect:/security/security_list/?currencySeries=" + security.getCurrencies().getCurrencySeries() + "&curId=" + security.getCurrencies().getId();
    }
}
