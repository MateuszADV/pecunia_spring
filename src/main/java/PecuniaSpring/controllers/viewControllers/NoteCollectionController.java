package PecuniaSpring.controllers.viewControllers;

import PecuniaSpring.models.Continent;
import PecuniaSpring.models.Currency;
import PecuniaSpring.models.Note;
import PecuniaSpring.models.dto.continent.ContinentCountriesDto;
import PecuniaSpring.models.dto.note.NoteDto;
import PecuniaSpring.models.repositories.CurrencyRepository;
import PecuniaSpring.models.repositories.NoteRepository;
import PecuniaSpring.models.sqlClass.CountryByStatus;
import PecuniaSpring.models.sqlClass.CurrencyByStatus;
import PecuniaSpring.security.config.UserCheckLoged;
import PecuniaSpring.services.countryServices.CountryServiceImpl;
import PecuniaSpring.services.noteServices.NoteServiceImpl;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import utils.JsonUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class NoteCollectionController {

    private NoteServiceImpl noteService;
    private CountryServiceImpl countryService;
    private UserCheckLoged userCheckLoged;

    private NoteRepository noteRepository;
    private CurrencyRepository currencyRepository;

    @Autowired
    public NoteCollectionController(NoteServiceImpl noteService, CountryServiceImpl countryService, UserCheckLoged userCheckLoged, NoteRepository noteRepository) {
        this.noteService = noteService;
        this.countryService = countryService;
        this.userCheckLoged = userCheckLoged;
        this.noteRepository = noteRepository;
    }


    @GetMapping("/note/collection")
    public String getIndex(ModelMap modelMap) {
        String role = userCheckLoged.UserCheckLoged().getAuthorities().toArray()[0].toString();
        System.out.println(JsonUtils.gsonPretty(userCheckLoged.UserCheckLoged()));
        System.out.println(role);
            modelMap.addAttribute("continents", countryService.countryCounts());

        return "note/collection/index";
    }

    @GetMapping("/note/collection/country")
    public String getCountry(@RequestParam("selectContinent") String continentEn,
                             ModelMap modelMap) {
        String role = userCheckLoged.UserCheckLoged().getAuthorities().toArray()[0].toString();

        System.out.println(continentEn);
        List<CountryByStatus> countryByStatusList = new ArrayList<>();
        if (role == "ADMIN") {
            countryByStatusList = noteService.getCountryByStatus(continentEn, "KOLEKCJA", role);
        } else {
            countryByStatusList = noteService.getCountryByStatus(continentEn, "KOLEKCJA", role);
            System.out.println("Brak uprawnień");
            modelMap.addAttribute("error", "Brak Uprawnień");
            return "error";
        }
        modelMap.addAttribute("countryByStatusList", countryByStatusList);


        System.out.println(JsonUtils.gsonPretty(countryByStatusList));

        return "note/collection/country";
    }

    @GetMapping("/note/collection/currency")
    public String getCurrency(@RequestParam("selectCountryId") Long countryId, ModelMap modelMap) {
        String role = userCheckLoged.UserCheckLoged().getAuthorities().toArray()[0].toString();

        System.out.println(countryId);
//        List<Object[]> objects = noteRepository.currencyByStatus("KOLEKCJA", countryId);
        List<CurrencyByStatus> currencyByStatusList = new ArrayList<>();
        if (role == "ADMIN") {
            currencyByStatusList = noteService.getCurrencyByStatus(countryId, "KOLEKCJA", role);
        } else {
            currencyByStatusList = noteService.getCurrencyByStatus(countryId, "KOLEKCJA", role);
        }
        modelMap.addAttribute("currencyByStatusList", currencyByStatusList);
        System.out.println(JsonUtils.gsonPretty(currencyByStatusList));
        return "note/collection/currency";
    }

    @GetMapping("/note/collection/notes")
    public String getNote(@RequestParam("selectCurrencyId") Long currencyId, ModelMap modelMap) {
//        String role = userCheckLoged.UserCheckLoged().getAuthorities().toArray()[0].toString();
//        List<Note> notes = new ArrayList<>();
//        List<NoteDto> noteDtos = new ArrayList<>();
//
//        if (role == "ADMIN") {
//            notes = noteService.getNoteByCurrencyId(currencyId, role);
//            for (Note note : notes) {
//                noteDtos.add(new ModelMapper().map(note, NoteDto.class));
//            }
//        } else {
//            notes = noteService.getNoteByCurrencyId(currencyId, role);
//            if (notes.size() > 0) {
//                for (Note note : notes) {
//                    noteDtos.add(new ModelMapper().map(note, NoteDto.class));
//                }
//            } else {
//                modelMap.addAttribute("error" , "Brak danych do wyświetlenia!!!");
//                return "error";
//            }
//        }
//        System.out.println(JsonUtils.gsonPretty(noteDtos));
//
//        modelMap.addAttribute("notes", noteDtos);


//        return "note/collection/notes";
        return findPaginated(1, currencyId, modelMap);
    }

    @GetMapping("/pageNote/{pageNo}")
    public String findPaginated(@PathVariable(value = "pageNo") int pageNo,
                                @RequestParam("currencyId") Long currencyId, ModelMap modelMap) {
        String role = userCheckLoged.UserCheckLoged().getAuthorities().toArray()[0].toString();
//        Integer pageNo = 1;
        Integer pageSize =10;
//        Long currencyId = 226l;

        Page<Note> page = noteService.findNotePaginated(pageNo, pageSize, currencyId, role);
        List<NoteDto> noteDtoList = new ArrayList<>();

        for (Note note : page.getContent()) {
            noteDtoList.add(new ModelMapper().map(note, NoteDto.class));
        }

        String pathPage = "/pageNote/";
        modelMap.addAttribute("currentPage", pageNo);
        modelMap.addAttribute("totalPages", page.getTotalPages());
        modelMap.addAttribute("totalItems", page.getTotalElements());
        modelMap.addAttribute("pageSize", pageSize);
        modelMap.addAttribute("pathPage", pathPage);

        System.out.println("PPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPP");
        System.out.println(page.getTotalElements());
        System.out.println(page.getTotalPages());
        System.out.println(page.getSize());

        for (NoteDto noteDto : noteDtoList) {
            System.out.println(noteDto.getId() + " - " + noteDto.getDenomination());
        }

        modelMap.addAttribute("notes", noteDtoList);
        System.out.println("PPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPP");
        return "note/collection/notes";
    }

    @GetMapping("/note/collection/show/{noteId}")
    public String getShow(@PathVariable Long noteId, ModelMap modelMap) {
        System.out.println("pppppppppppppppppppppppppppppppppppppppppppp");
        System.out.println(noteId);
        System.out.println("pppppppppppppppppppppppppppppppppppppppppppp");

        Note note = noteService.getNoteById(noteId);
        NoteDto noteDto = new ModelMapper().map(note, NoteDto.class);
        modelMap.addAttribute("note", noteDto);
        System.out.println(noteDto.getCurrencies().getId());

        return "note/collection/show";
    }
}
