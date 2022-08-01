package PecuniaSpring.controllers;

import PecuniaSpring.models.Country;
import PecuniaSpring.models.repsitories.CountryRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

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
}
