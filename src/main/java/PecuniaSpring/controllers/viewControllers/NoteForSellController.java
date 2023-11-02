package PecuniaSpring.controllers.viewControllers;

import PecuniaSpring.models.repositories.NoteRepository;
import PecuniaSpring.models.sqlClass.CountryByStatus;
import PecuniaSpring.models.sqlClass.GetNotesByStatus;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import utils.JsonUtils;

import java.util.ArrayList;
import java.util.List;

@Controller
@AllArgsConstructor
public class NoteForSellController {

    private NoteRepository noteRepository;

    @GetMapping("/note/forSell")
    public String getindex(ModelMap modelMap) {
        List<Object[]> objects;
        List<CountryByStatus> countryByStatusList = new ArrayList<>();
        objects = noteRepository.countryByStatus("FOR SELL");
        try {
            System.out.println(JsonUtils.gsonPretty(objects));
            for (Object[] object : objects) {
                countryByStatusList.add(new ModelMapper().map(object[0], CountryByStatus.class));
            }

            modelMap.addAttribute("countryByStatusList", countryByStatusList);
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return "note/forSell/index";
    }

    @GetMapping("/note/forSell/country/notes/{countryId}")
    public String getCountryNotes(@PathVariable("countryId") Long countryId, ModelMap modelMap) {
        System.out.println("[[[[[[[[[[[[[[[[[[[[]]]]]]]]]]]]]]]]]]]]]]]]]]]C");
        System.out.println(countryId);
        System.out.println("[[[[[[[[[[[[[[[[[[[[]]]]]]]]]]]]]]]]]]]]]]]]]]]C");

        List<Object[]> objects;
        objects = noteRepository.getNotesByStatus("FOR SELL", countryId);
        List<GetNotesByStatus> getNotesByStatusList = new ArrayList<>();
        for (Object[] object : objects) {
            getNotesByStatusList.add(new ModelMapper().map(object[0],GetNotesByStatus.class));
        }

        modelMap.addAttribute("forSellNotesList", getNotesByStatusList);
        System.out.println(JsonUtils.gsonPretty(getNotesByStatusList));
        return "note/forSell/notes";
    }
    @GetMapping("/note/forSell/list")
    private String getNoteForSellList(ModelMap modelMap) {
        List<Object[]> objects = noteRepository.getNotesByStatus("FOR SELL");
        List<GetNotesByStatus> getNotesByStatusList = new ArrayList<>();
        for (Object[] object : objects) {
            getNotesByStatusList.add(new ModelMapper().map(object[0],GetNotesByStatus.class));
        }

        modelMap.addAttribute("forSellNotesList", getNotesByStatusList);
        return "note/forSell/list";
    }
}
