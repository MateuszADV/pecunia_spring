package PecuniaSpring.controllers.viewControllers;

import PecuniaSpring.models.Continent;
import PecuniaSpring.models.repositories.ContinentRepository;
import PecuniaSpring.services.continentServices.ContinentService;
import PecuniaSpring.services.continentServices.ContinentServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import utils.JsonUtils;

import java.util.List;

@Controller
@AllArgsConstructor
public class ContinentController {

    private ContinentRepository continentRepository;
    private ContinentServiceImpl continentService;

    @GetMapping("/continent")
    public String getIndex(ModelMap modelMap) {
        List<Continent> continents = continentRepository.findAll();
        System.out.println(continents);
        modelMap.addAttribute("continents", continents);

        String json = JsonUtils.gsonPretty(continents.get(0));
        System.out.println(json);
        return "continent/index";
    }

    @GetMapping("/continent/new")
    public String getNew(ModelMap modelMap) {
        modelMap.addAttribute("continentForm", new Continent());
        System.out.println("------------------NEW Continent----------------------");

        return "continent/new";
    }

    @PostMapping("/continent/new")
    public String postNew(@ModelAttribute("countryForm")Continent continentForm, ModelMap modelMap) {
        System.out.println("--------------------***************START*****************-----------------------------");
        System.out.println(JsonUtils.gsonPretty(continentForm));
        continentService.saveContinent(continentForm);
        System.out.println("--------------------*****************END***************-----------------------------");
        List<Continent> continents = continentRepository.findAll();
        modelMap.addAttribute("continents", continents);

        return "continent/index";
    }

    @GetMapping("/continent/")
    public String getNContinent(@RequestParam("selectContinent") String continentEn,
                                ModelMap modelMap) {
        System.out.println("====================Continent ID============================");
        System.out.println(continentEn);
        modelMap.addAttribute("continents", continentRepository.findAll());
        return "continent/index";
    }
}
