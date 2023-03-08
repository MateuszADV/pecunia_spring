package PecuniaSpring.controllers.viewControllers;

import PecuniaSpring.models.Continent;
import PecuniaSpring.models.Note;
import PecuniaSpring.models.dto.continent.ContinentCountriesDto;
import PecuniaSpring.models.dto.note.NoteDto;
import PecuniaSpring.models.repositories.NoteRepository;
import PecuniaSpring.models.sqlClass.CountryByStatus;
import PecuniaSpring.models.sqlClass.CurrencyByStatus;
import PecuniaSpring.security.config.UserCheckLoged;
import PecuniaSpring.services.countryServices.CountryServiceImpl;
import PecuniaSpring.services.noteServices.NoteServiceImpl;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import utils.JsonUtils;

import java.util.ArrayList;
import java.util.List;

@Controller
public class NoteCollectionController {

    private NoteServiceImpl noteService;
    private CountryServiceImpl countryService;
    private UserCheckLoged userCheckLoged;

    private NoteRepository noteRepository;

    @Autowired
    public NoteCollectionController(NoteServiceImpl noteService, CountryServiceImpl countryService, UserCheckLoged userCheckLoged, NoteRepository noteRepository) {
        this.noteService = noteService;
        this.countryService = countryService;
        this.userCheckLoged = userCheckLoged;
        this.noteRepository = noteRepository;
    }

    private String role = "";


    @GetMapping("/note/collection")
    public String getIndex(ModelMap modelMap) {
        role = userCheckLoged.UserCheckLoged().getAuthorities().toArray()[0].toString();
        System.out.println(role);
            modelMap.addAttribute("continents", countryService.countryCounts());

        return "note/collection/index";
    }

    @GetMapping("/note/collection/country")
    public String getCountry(@RequestParam("selectContinent") String continentEn,
                             ModelMap modelMap) {
        System.out.println(continentEn);
        List<CountryByStatus> countryByStatusList = new ArrayList<>();
        if (role == "ADMIN") {
            countryByStatusList = noteService.getCountryByStatus(continentEn, "KOLEKCJA", "ADMIN");
        } else {
            countryByStatusList = noteService.getCountryByStatus(continentEn, "KOLEKCJA", "USER");
            System.out.println("Brak uprawnień");
        }
        modelMap.addAttribute("countryByStatusList", countryByStatusList);


        System.out.println(JsonUtils.gsonPretty(countryByStatusList));

        return "note/collection/country";
    }

    @GetMapping("/note/collection/currency")
    public String getCurrency(@RequestParam("selectCountryId") Long countryId, ModelMap modelMap) {
        System.out.println(countryId);
        List<Object[]> objects = noteRepository.currencyByStatus("KOLEKCJA", countryId);
        List<CurrencyByStatus> currencyByStatusList = new ArrayList<>();
        for (Object[] object : objects) {
            currencyByStatusList.add(new ModelMapper().map(object[0], CurrencyByStatus.class));
        }
        modelMap.addAttribute("currencyByStatusList", currencyByStatusList);
        System.out.println(JsonUtils.gsonPretty(currencyByStatusList));
        return "note/collection/currency";
    }

    @GetMapping("/note/collection/notes")
    public String getNote(@RequestParam("selectCurrencyId") Long currencyId, ModelMap modelMap) {
        System.out.println(currencyId);
        List<Note> notes = noteService.getNoteByCurrencyId(currencyId);
        List<NoteDto> noteDtos = new ArrayList<>();
        for (Note note : notes) {
            noteDtos.add(new ModelMapper().map(note, NoteDto.class));
        }
        System.out.println(JsonUtils.gsonPretty(noteDtos));

        modelMap.addAttribute("notes", noteDtos);
        return "note/collection/notes";
    }
}
