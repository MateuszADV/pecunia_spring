package PecuniaSpring.controllers.viewControllers;

import PecuniaSpring.models.Continent;
import PecuniaSpring.models.dto.continent.ContinentCountriesDto;
import PecuniaSpring.models.repositories.NoteRepository;
import PecuniaSpring.models.sqlClass.CountryByStatus;
import PecuniaSpring.services.countryServices.CountryServiceImpl;
import PecuniaSpring.services.noteServices.NoteServiceImpl;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import utils.JsonUtils;

import java.util.ArrayList;
import java.util.List;

@Controller
@AllArgsConstructor
public class NoteCollectionController {

    private NoteServiceImpl noteService;
    private CountryServiceImpl countryService;

    private NoteRepository noteRepository;


    @GetMapping("/note/collection")
    public String getIndex(ModelMap modelMap) {

        modelMap.addAttribute("continents", countryService.countryCounts());
        return "note/collection/index";
    }

    @GetMapping("/note/collection/country")
    public String getCountry(@RequestParam("selectContinent") String continentEn,
                             ModelMap modelMap) {
        System.out.println(continentEn);
        List<CountryByStatus> countryByStatusList = noteService.getCountryByStatus(continentEn, "KOLEKCJA");

        modelMap.addAttribute("countryByStatusList", countryByStatusList);
        System.out.println(JsonUtils.gsonPretty(countryByStatusList));

        return "note/collection/country";
    }

    @GetMapping("/note/collection/currency")
    public String getCurrency(@RequestParam("selectCountryId") Long countryId, ModelMap modelMap) {
        System.out.println(countryId);
        List<Object[]> objects = noteRepository.currencyByStatus("KOLEKCJA", countryId);

        System.out.println(JsonUtils.gsonPretty(objects));
        return "note/collection/currency";
    }
}
