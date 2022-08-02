package PecuniaSpring.controllers;

import PecuniaSpring.models.Country;
import PecuniaSpring.models.repsitories.CountryRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Controller
public class CountryController {

    private CountryRepository countryRepository;

    @GetMapping("/country")
    public String getIndex(ModelMap modelMap)
    {
        List<Country> countries = countryRepository.findAll();
        modelMap.addAttribute("countries", countries);
        return "country/index";
    }

    @GetMapping("/country/show/{countryId}")
    public String getShow(@PathVariable Long countryId, ModelMap modelMap) {
        System.out.println("+++++++++++++++++++++TU JESTEM+++++++++++++++++++++++++++++++++++++");
        System.out.println("Id - " + countryId);
        Optional<Country> country = countryRepository.findById(countryId);
        System.out.println(country.isPresent());
        System.out.println(country.get().getCountry_en());
        modelMap.addAttribute("country", country);
        return "country/show";
    }

    @GetMapping("/country/new")
    public String getNew(ModelMap modelMap) {
        modelMap.addAttribute("countryForm", new Country());
        System.out.println("------------------NEW COUNTRY----------------------");

        return "country/new";
    }

    @PostMapping("/country/new")
    public String postRegistration(@ModelAttribute("countryForm")Country country, ModelMap modelMap) {
        System.out.println("--------------------********************************-----------------------------");
        System.out.println(country.toString());
        countryRepository.save(country);
        System.out.println("--------------------********************************-----------------------------");
        List<Country> countries = countryRepository.findAll();
        modelMap.addAttribute("countries", countries);

        return "country/index";
    }
}
