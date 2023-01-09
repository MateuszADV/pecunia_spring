package PecuniaSpring.controllers.viewControllers;

import PecuniaSpring.models.Country;
import PecuniaSpring.models.Currency;
import PecuniaSpring.models.Note;
import PecuniaSpring.models.dto.country.CountryDtoForm;
import PecuniaSpring.models.dto.currency.CurrencyDto;
import PecuniaSpring.models.dto.note.NoteDto;
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
        List<CurrencyDto> currencyDtos = new ArrayList<>();
        for (Currency currency : currencies) {
            currencyDtos.add(new ModelMapper().map(currency, CurrencyDto.class));
        }

        System.out.println("=======================START===========================");
        System.out.println(countryEn);
        System.out.println(JsonUtils.gsonPretty(countryDto));
        System.out.println("---------------------------------------------------------");
        System.out.println(currencyDtos.size());
        for (CurrencyDto currencyDto : currencyDtos) {
            System.out.println(currencyDto.getCurrencySeries());
        }
        System.out.println(JsonUtils.gsonPretty(currencyDtos));
        System.out.println("=======================STOP===========================");
        modelMap.addAttribute("currencies", currencyDtos);
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
        CurrencyDto currencyDto = new ModelMapper().map(currency, CurrencyDto.class);
        List<Note> notes = noteService.getNoteByCurrencyId(currencyId);
        List<NoteDto> noteDtos = new ArrayList();
        for (Note note : notes) {
            noteDtos.add(new ModelMapper().map(note, NoteDto.class));
        }

        System.out.println(notes.size());
        System.out.println(JsonUtils.gsonPretty(noteDtos));

        modelMap.addAttribute("currency", currencyDto);
        modelMap.addAttribute("notes", noteDtos);
        return "/note/note_list";
    }
}
