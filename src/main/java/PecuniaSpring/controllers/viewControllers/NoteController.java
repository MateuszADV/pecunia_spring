package PecuniaSpring.controllers.viewControllers;

import PecuniaSpring.models.Country;
import PecuniaSpring.models.Currency;
import PecuniaSpring.models.Note;
import PecuniaSpring.models.dto.country.CountryDtoForm;
import PecuniaSpring.models.dto.currency.CurrencyDto;
import PecuniaSpring.models.dto.currency.CurrencyDtoByNote;
import PecuniaSpring.models.dto.note.NoteDto;
import PecuniaSpring.models.dto.note.NoteDtoByCurrency;
import PecuniaSpring.models.repositories.NoteRepository;
import PecuniaSpring.services.countryServices.CountryServiceImpl;
import PecuniaSpring.services.currencyService.CurrencyServiceImpl;
import PecuniaSpring.services.noteServices.NoteServiceImpl;
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
import java.util.Optional;

@Controller
@AllArgsConstructor
public class NoteController {

    private NoteRepository noteRepository;
    private NoteServiceImpl noteService;
    private CountryServiceImpl countryService;
    private CurrencyServiceImpl currencyService;

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

        System.out.println(currencySeries);
        System.out.println(currencyId);
        Currency currency = currencyService.getCurrencyById(currencyId);
        CurrencyDtoByNote currencyDtoByNote = new ModelMapper().map(currency, CurrencyDtoByNote.class);
        List<Note> notes = noteService.getNoteByCurrencyId(currencyId);
        List<NoteDtoByCurrency> noteDtoByCurrencies = new ArrayList();
        for (Note note : notes) {
            noteDtoByCurrencies.add(new ModelMapper().map(note, NoteDtoByCurrency.class));
        }

        System.out.println(notes.size());
        System.out.println(JsonUtils.gsonPretty(noteDtoByCurrencies));

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
}
