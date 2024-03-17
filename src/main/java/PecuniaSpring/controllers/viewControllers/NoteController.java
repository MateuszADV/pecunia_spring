package PecuniaSpring.controllers.viewControllers;

import PecuniaSpring.models.*;
import PecuniaSpring.models.dto.active.ActiveDtoSelect;
import PecuniaSpring.models.dto.bought.BoughtDto;
import PecuniaSpring.models.dto.country.CountryDtoForm;
import PecuniaSpring.models.dto.currency.CurrencyDto;
import PecuniaSpring.models.dto.currency.CurrencyDtoByPattern;
import PecuniaSpring.models.dto.imageType.ImageTypeDtoSelect;
import PecuniaSpring.models.dto.making.MakingDtoSelect;
import PecuniaSpring.models.dto.note.NoteDto;
import PecuniaSpring.models.dto.note.NoteDtoByCurrency;
import PecuniaSpring.models.dto.note.NoteFormDto;
import PecuniaSpring.models.dto.quality.QualityDtoSelect;
import PecuniaSpring.models.dto.status.StatusDtoSelect;
import PecuniaSpring.models.other.variable.VariableForm;
import PecuniaSpring.models.repositories.NoteRepository;
import PecuniaSpring.services.activeService.ActiveServiceImpl;
import PecuniaSpring.services.boughtServices.BoughtServicesImpl;
import PecuniaSpring.services.countryServices.CountryServiceImpl;
import PecuniaSpring.services.currencyService.CurrencyServiceImpl;
import PecuniaSpring.services.imageTypeService.ImageTypeSeviceImpl;
import PecuniaSpring.services.makingServices.MakingServiceImpl;
import PecuniaSpring.services.noteServices.NoteServiceImpl;
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
import javax.validation.Valid;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@AllArgsConstructor
public class NoteController {

    private NoteRepository noteRepository;
    private NoteServiceImpl noteService;
    private CountryServiceImpl countryService;
    private CurrencyServiceImpl currencyService;
    private BoughtServicesImpl boughtServices;
    private ActiveServiceImpl activeService;
    private MakingServiceImpl makingService;
    private QualityServiceImpl qualityService;
    private StatusServiceImpl statusService;
    private ImageTypeSeviceImpl imageTypeSevice;

    @GetMapping("/note")
    public String getIndex(ModelMap modelMap) {

        return getSearch("", modelMap);
    }

    @GetMapping("/note/currency/{countryEn}")
    public String getNoteCurrency(@PathVariable String countryEn, ModelMap modelMap) {
        Country country = countryService.getCountyByCountryEn(countryEn);
        CountryDtoForm countryDto = new ModelMapper().map(country, CountryDtoForm.class);
        List<Currency> currencies = currencyService.getCurrencyByCountryByPattern(countryDto.getId(), "NOTE");
        List<CurrencyDtoByPattern> currencyDtoByPatterns = new ArrayList<>();
        for (Currency currency : currencies) {
            currencyDtoByPatterns.add(new ModelMapper().map(currency, CurrencyDtoByPattern.class));
        }

        System.out.println("=======================START===========================");
        System.out.println(countryEn);
        System.out.println(JsonUtils.gsonPretty(countryDto));
        System.out.println("---------------------------------------------------------");
        System.out.println(currencyDtoByPatterns.size());
        for (CurrencyDtoByPattern currencyDtoByPattern : currencyDtoByPatterns) {
            System.out.println(currencyDtoByPattern.getCurrencySeries());
        }
        System.out.println(JsonUtils.gsonPretty(currencyDtoByPatterns));
        System.out.println("=======================STOP===========================");
        modelMap.addAttribute("currencies", currencyDtoByPatterns);
        return "note/currency";
    }

    @PostMapping("/note/search")
    public String getSearch(@RequestParam(value = "keyword") String keyword, ModelMap modelMap) {
        Search.searchCountry(keyword, modelMap, countryService);
//        System.out.println(JsonUtils.gsonPretty(countryGetDtos));
        return "note/index";
    }

    @GetMapping("/note/note_list")
    public String getNoteList(@RequestParam(value = "currencySeries") String currencySeries,
                              @RequestParam(value = "curId") Long  currencyId,
                              HttpServletRequest request,
                              ModelMap modelMap) {
        Currency currency = currencyService.getCurrencyById(currencyId);
        CurrencyDtoByPattern currencyDtoByPattern = new ModelMapper().map(currency, CurrencyDtoByPattern.class);
        List<Note> notes = noteService.getNoteByCurrencyId(currencyId);
        List<NoteDtoByCurrency> noteDtoByCurrencies = new ArrayList();
        for (Note note : notes) {
            noteDtoByCurrencies.add(new ModelMapper().map(note, NoteDtoByCurrency.class));
        }
        modelMap.addAttribute("currency", currencyDtoByPattern);
        modelMap.addAttribute("notes", noteDtoByCurrencies);
        return "/note/note_list";
    }

    @GetMapping("/note/show/{noteId}")
    public String getShowNote(@PathVariable Long noteId, ModelMap modelMap) {
        System.out.println(noteId);
        Note note = noteService.getNoteById(noteId);
        NoteDto noteDto = new ModelMapper().map(note, NoteDto.class);
        System.out.println(JsonUtils.gsonPretty(noteDto));

        modelMap.addAttribute("note", noteDto);
        modelMap.addAttribute("json", JsonUtils.gsonPretty(noteDto));
        return "/note/show";
    }

    @GetMapping("/note/new")
    public String getNew(@RequestParam(value = "curId") Long currencyId,
                         ModelMap modelMap) {
        System.out.println("================================BEGIN===============================");
        Currency currency = currencyService.getCurrencyById(currencyId);
        CurrencyDto currencyDto = new ModelMapper().map(currency, CurrencyDto.class);
        noteFormVariable(modelMap, currency);
        System.out.println("================================END===============================");

        NoteFormDto noteFormDto = new NoteFormDto();
        noteFormDto.setCurrencies(currencyDto);

        noteFormDto.setDateBuy(Date.valueOf(LocalDate.now()));
        noteFormDto.setPriceBuy(0.00);
        noteFormDto.setPriceSell(0.00);
        modelMap.addAttribute("noteForm", noteFormDto);
         return "note/new";
    }

    @PostMapping("/note/new")
    public String postNew(@ModelAttribute ("noteForm")@Valid NoteFormDto noteForm, BindingResult result,
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
//                result.rejectValue("dateBuy", "typeMismatch", "Błąd Testowy????");
                modelMap.addAttribute("errorDateBuy", "Podaj porawną datę");
            }
            System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&ERROR END&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&");

            Currency currency = currencyService.getCurrencyById(noteForm.getCurrencies().getId());
            noteFormVariable(modelMap, currency);
            return "note/new";
        }

        Currency currency = currencyService.getCurrencyById(noteForm.getCurrencies().getId());
        System.out.println("++++++++++++++++++++++++++++++START++++++++++++++++++++++++++++++");
        System.out.println(JsonUtils.gsonPretty(noteForm));
        System.out.println("++++++++++++++++++++++++++++++STOP+++++++++++++++++++++++++++++++");

        Note note = new ModelMapper().map(noteForm, Note.class);
        noteRepository.save(note);
        return "redirect:/note/note_list/?currencySeries=" + currency.getCurrencySeries() + "&curId=" + currency.getId();
    }

    @GetMapping("/note/edit/{noteId}")
    public String getEdit(@PathVariable Long noteId, ModelMap modelMap) {
        Optional<Note> note = Optional.ofNullable(noteService.getNoteById(noteId));
        NoteFormDto noteFormDto = new ModelMapper().map(note, NoteFormDto.class);
        System.out.println(JsonUtils.gsonPretty(noteFormDto));
        modelMap.addAttribute("noteForm", noteFormDto);
        modelMap.addAttribute("noteInfoLightBox",noteFormDto);
        noteFormVariable(modelMap, note.get().getCurrencies());
        return "note/edit";
    }

    @PostMapping("/note/edit")
    public String postEdit(@ModelAttribute ("noteForm")@Valid NoteFormDto noteForm, BindingResult result,
                           HttpServletRequest request,
                           ModelMap modelMap) {
        Currency currencyTmp = currencyService.getCurrencyById(noteForm.getCurrencies().getId());
        noteFormVariable(modelMap, currencyTmp);
        if (result.hasErrors()) {
            System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&ERROR&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&");

            Optional<Note> note = Optional.ofNullable(noteService.getNoteById(noteForm.getId()));
            NoteFormDto noteInfoLightBox = new ModelMapper().map(note, NoteFormDto.class);
            modelMap.addAttribute("noteInfoLightBox", noteInfoLightBox);

            System.out.println(result.toString());
            System.out.println(result.hasFieldErrors("dateBuy"));
            System.out.println(result.resolveMessageCodes("test błedu", "dateBuy").toString());

            if (result.hasFieldErrors("dateBuy")) {
                System.out.println(result.getFieldError("dateBuy").getDefaultMessage());
                System.out.println(result.getFieldError("dateBuy").getCode());
//                result.rejectValue("dateBuy", "typeMismatch", "Błąd Testowy????");
                modelMap.addAttribute("errorDateBuy", "Podaj porawną datę");
            }
            System.out.println(JsonUtils.gsonPretty(noteForm.getCurrencies()));

            System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&ERROR END&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&");

            return "note/edit";
        }

        Currency currency = currencyService.getCurrencyById(noteForm.getCurrencies().getId());
        System.out.println("++++++++++++++++++++++++++++++START++++++++++++++++++++++++++++++");
        System.out.println(JsonUtils.gsonPretty(noteForm));
        System.out.println("++++++++++++++++++++++++++++++STOP+++++++++++++++++++++++++++++++");

        Note note = new ModelMapper().map(noteForm, Note.class);
        noteRepository.save(note);

//        return getNoteList(currency.getCurrencySeries(), currency.getId(), request, modelMap);
        return "redirect:/note/note_list/?currencySeries=" + currency.getCurrencySeries() + "&curId=" + currency.getId();

    }


    @GetMapping("/note/delete/{noteId}")
    public String getDelete(@PathVariable Long noteId, HttpServletRequest request, ModelMap modelMap) {
        Note note = noteService.getNoteById(noteId);
        System.out.println("^^^^^^^^^^^^^^^^^^^^^^^BEGIN^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
        System.out.println(note.getCurrencies().getCurrencySeries());
        System.out.println(note.getCurrencies().getId());
        System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^END^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");

        noteService.deleteNoteById(noteId);
        return "redirect:/note/note_list/?currencySeries=" + note.getCurrencies().getCurrencySeries() + "&curId=" + note.getCurrencies().getId();
    }

    private void noteFormVariable(ModelMap modelMap, Currency currency) {
        List<Currency> currenciesList = currencyService.getCurrencyByCountryByPattern(currency.getCountries().getId(), "NOTE");
        List<CurrencyDto> currencyDtos = new ArrayList<>();
        for (Currency currency1 : currenciesList) {
            currencyDtos.add(new ModelMapper().map(currency1, CurrencyDto.class));
        }
        System.out.println(JsonUtils.gsonPretty(currencyDtos));

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

        System.out.println("##############################################");

        VariableForm.variableToSelect(modelMap, currencyDtos, boughtDtos, activeDtoSelects, makingDtoSelects, qualityDtoSelects, statusDtoSelects, imageTypeDtoSelects);
    }
}
