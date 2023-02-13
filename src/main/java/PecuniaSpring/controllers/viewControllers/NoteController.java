package PecuniaSpring.controllers.viewControllers;

import PecuniaSpring.models.*;
import PecuniaSpring.models.dto.active.ActiveDtoSelect;
import PecuniaSpring.models.dto.bought.BoughtDto;
import PecuniaSpring.models.dto.country.CountryDtoForm;
import PecuniaSpring.models.dto.currency.CurrencyDto;
import PecuniaSpring.models.dto.currency.CurrencyDtoByNote;
import PecuniaSpring.models.dto.note.NoteDto;
import PecuniaSpring.models.dto.note.NoteDtoByCurrency;
import PecuniaSpring.models.dto.note.NoteFormDto;
import PecuniaSpring.models.repositories.NoteRepository;
import PecuniaSpring.services.activeService.ActiveServiceImpl;
import PecuniaSpring.services.boughtServices.BoughtServicesImpl;
import PecuniaSpring.services.countryServices.CountryServiceImpl;
import PecuniaSpring.services.currencyService.CurrencyServiceImpl;
import PecuniaSpring.services.noteServices.NoteServiceImpl;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
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

    Optional<Note> noteTmp;

    @GetMapping("/note")
    public String getIndex(ModelMap modelMap) {

        return getSearch("", modelMap);
    }

    @GetMapping("/note/currency/{countryEn}")
    public String getNoteCurrency(@PathVariable String countryEn, ModelMap modelMap) {
        Country country = countryService.getCountyByCountryEn(countryEn);
        CountryDtoForm countryDto = new ModelMapper().map(country, CountryDtoForm.class);
        List<Currency> currencies = currencyService.getCurrencyByCountryByPattern(countryDto.getId(), "NOTE");
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

//        System.out.println(currencySeries);
//        System.out.println(currencyId);
        Currency currency = currencyService.getCurrencyById(currencyId);
        CurrencyDtoByNote currencyDtoByNote = new ModelMapper().map(currency, CurrencyDtoByNote.class);
        List<Note> notes = noteService.getNoteByCurrencyId(currencyId);
        List<NoteDtoByCurrency> noteDtoByCurrencies = new ArrayList();
        for (Note note : notes) {
            noteDtoByCurrencies.add(new ModelMapper().map(note, NoteDtoByCurrency.class));
        }

//        System.out.println(notes.size());
//        System.out.println(JsonUtils.gsonPretty(noteDtoByCurrencies));

        modelMap.addAttribute("currency", currencyDtoByNote);
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
//        **********************************************
//        *****Kolumny do usunięcia z tabeli NOte*******
//        **********************************************

        Bought bought = boughtServices.getBoughtById(noteForm.getBoughts().getId());
        note.setBought(bought.getName());

        Active active = activeService.getActiveById(noteForm.getActives().getId());
        note.setSignatureCode(active.getActiveCod());

//        **********************************************

        noteRepository.save(note);

        return getNoteList(currency.getCurrencySeries(), currency.getId(), request, modelMap);
    }

    @GetMapping("/note/edit/{noteId}")
    public String getEdit(@PathVariable Long noteId, ModelMap modelMap) {
        noteTmp = Optional.ofNullable(noteService.getNoteById(noteId));
        NoteFormDto noteFormDto = new ModelMapper().map(noteTmp, NoteFormDto.class);
        System.out.println(JsonUtils.gsonPretty(noteFormDto));
        modelMap.addAttribute("noteForm", noteFormDto);
        noteFormVariable(modelMap, noteTmp.get().getCurrencies());
        return "note/edit";
    }

    @PostMapping("/note/edit")
    public String postEdit(@ModelAttribute ("noteForm")@Valid NoteFormDto noteForm, BindingResult result,
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
            return "note/edit";
        }


        Currency currency = currencyService.getCurrencyById(noteForm.getCurrencies().getId());
        System.out.println("++++++++++++++++++++++++++++++START++++++++++++++++++++++++++++++");
        System.out.println(JsonUtils.gsonPretty(noteForm));
        System.out.println("------------------------------------------------------------------");
        noteForm.setId(noteTmp.get().getId());
        noteForm.setCreated_at(noteTmp.get().getCreated_at());
        System.out.println(JsonUtils.gsonPretty(noteForm));
        System.out.println("++++++++++++++++++++++++++++++STOP+++++++++++++++++++++++++++++++");



        Note note = new ModelMapper().map(noteForm, Note.class);
//        **********************************************
//        *****Kolumny do usunięcia z tabeli NOte*******
//        **********************************************

        Bought bought = boughtServices.getBoughtById(noteForm.getBoughts().getId());
        note.setBought(bought.getName());

        Active active = activeService.getActiveById(noteForm.getActives().getId());
        note.setSignatureCode(active.getActiveCod());

//        **********************************************

        System.out.println("######################################################");
        System.out.println("BANKNOT ZOSTAŁ ZAPISANY!!!!!!!!!!");
        noteRepository.save(note);
        System.out.println("######################################################");


        return getNoteList(currency.getCurrencySeries(), currency.getId(), request, modelMap);

    }

    @GetMapping("/note/delete/{noteId}")
    public String getDelete(@PathVariable Long noteId, HttpServletRequest request, ModelMap modelMap) {
        Note note = noteService.getNoteById(noteId);
        System.out.println("^^^^^^^^^^^^^^^^^^^^^^^BEGIN^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
        System.out.println(note.getCurrencies().getCurrencySeries());
        System.out.println(note.getCurrencies().getId());
        System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^END^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");

        noteService.deleteNoteById(noteId);

        return getNoteList(note.getCurrencies().getCurrencySeries(), note.getCurrencies().getId(), request, modelMap);
    }


    private void noteFormVariable(ModelMap modelMap, Currency currency) {
        List<Currency> currenciesList = currencyService.getCurrencyByCountryByPattern(currency.getCountries().getId(), "NOTE");
        List<CurrencyDto> currencyDtos = new ArrayList<>();
        for (Currency currency1 : currenciesList) {
            currencyDtos.add(new ModelMapper().map(currency1, CurrencyDto.class));
        }
//        System.out.println(JsonUtils.gsonPretty(currencyDtos));

        List<Bought> boughts = boughtServices.getAllBought();
        List<BoughtDto> boughtDtos = new ArrayList<>();
        for (Bought bought : boughts) {
            boughtDtos.add(new ModelMapper().map(bought, BoughtDto.class));
        }
//        System.out.println(JsonUtils.gsonPretty(boughtDtos));

        List<Active> actives = activeService.getAllActive();
        List<ActiveDtoSelect> activeDtoSelects = new ArrayList<>();
        for (Active active : actives) {
            activeDtoSelects.add(new ModelMapper().map(active, ActiveDtoSelect.class));
        }

        modelMap.addAttribute("currencies", currencyDtos);
        modelMap.addAttribute("boughts", boughtDtos);
        modelMap.addAttribute("actives", activeDtoSelects);
        modelMap.addAttribute("standartDate", Date.valueOf(LocalDate.now()));
    }
}
