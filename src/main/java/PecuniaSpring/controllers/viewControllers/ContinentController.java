package PecuniaSpring.controllers.viewControllers;

import PecuniaSpring.controllers.dto.continent.ContinentCountriesDto;
import PecuniaSpring.controllers.dto.continent.ContinentDto;
import PecuniaSpring.models.Continent;
import PecuniaSpring.models.Country;
import PecuniaSpring.models.repositories.ContinentRepository;
import PecuniaSpring.services.continentServices.ContinentService;
import PecuniaSpring.services.continentServices.ContinentServiceImpl;
import PecuniaSpring.services.countryServices.CountryService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import utils.JsonUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@AllArgsConstructor
public class ContinentController {

    private ContinentRepository continentRepository;
    private ContinentServiceImpl continentService;
    private CountryService countryService;

    @GetMapping("/continent")
    public String getIndex(ModelMap modelMap) {
        getContinentList(modelMap);

        return "continent/index";
    }

    @GetMapping("/continent/new")
    public String getNew(ModelMap modelMap) {
        modelMap.addAttribute("continentForm", new ContinentDto());
        System.out.println("------------------NEW Continent----------------------");

        return "continent/new";
    }

    @PostMapping("/continent/new")
    public String postNew(@ModelAttribute("countryForm")ContinentDto continentForm, ModelMap modelMap) {
        System.out.println("--------------------***************START*****************-----------------------------");
        System.out.println(JsonUtils.gsonPretty(continentForm));
        Continent continent = new Continent();
        continent = new ModelMapper().map(continentForm, Continent.class);
        continentService.saveContinent(continent);
        System.out.println("--------------------*****************END***************-----------------------------");

        getContinentList(modelMap);

        return "continent/index";
    }

    private void getContinentList(ModelMap modelMap) {
        List<Continent> continents = continentRepository.findAll();
        List<ContinentDto> continentDtos = new ArrayList<>();
        for (Continent continent : continents) {
            continentDtos.add(new ModelMapper().map(continent, ContinentDto.class));
        }
        modelMap.addAttribute("continents", continentDtos);
    }

    @GetMapping("/continent/")
    public String getNContinent(@RequestParam("selectContinent") String continentEn,
                                ModelMap modelMap) {
        System.out.println("====================Continent ID============================");
        Continent continent = continentRepository.getContinent(continentEn);
        if (continent == null){
            getContinentList(modelMap);
            return getIndex(modelMap);
        }else {
            ContinentCountriesDto continentCountriesDto = new ModelMapper().map(continent, ContinentCountriesDto.class);
            System.out.println(JsonUtils.gsonPretty(continentCountriesDto));
            modelMap.addAttribute("continent", continentCountriesDto);
        }
        System.out.println("====================Continent ID============================");
        return "continent/country";
    }
}
