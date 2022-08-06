package PecuniaSpring.controllers;

import PecuniaSpring.models.Country;
import PecuniaSpring.models.repsitories.CountryRepository;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import utils.JsonUtils;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Controller
public class CountryController {

    private CountryRepository countryRepository;

    private Optional<Country> country;

    @GetMapping("/country")
    public String getIndex(ModelMap modelMap)
    {
//        List<Country> countries = countryRepository.findAll();
        List<Country> countries = countryRepository.countriesOrderByCountry_enAsc();
        modelMap.addAttribute("countries", countries);
        return "country/index";
    }

    @GetMapping("/country/show/{countryId}")
    public String getShow(@PathVariable Long countryId, ModelMap modelMap) {
        System.out.println("+++++++++++++++++++++TU JESTEM+++++++++++++++++++++++++++++++++++++");
        System.out.println("Id - " + countryId);
        Optional<Country> country = countryRepository.findById(countryId);
        System.out.println(country.isPresent());

        if (country.isPresent()) {
            modelMap.addAttribute("country", country);

            String json = JsonUtils.gsonPretty(country.get());
            System.out.println(json);
            modelMap.addAttribute("json", json);


            return "country/show";
        }
        else {
            modelMap.addAttribute("error", "Błedne Id - " + countryId);
            return "error";
        }

    }

    @GetMapping("/country/new")
    public String getNew(ModelMap modelMap) {
        modelMap.addAttribute("countryForm", new Country());
        System.out.println("------------------NEW COUNTRY----------------------");

        return "country/new";
    }

    @PostMapping("/country/new")
    public String postNew(@ModelAttribute("countryForm")Country countryForm, ModelMap modelMap) {
        System.out.println("--------------------***************START*****************-----------------------------");
        System.out.println(country.toString());

        System.out.println(JsonUtils.gsonPretty(countryForm));
        Gson gson = new Gson();

        countryRepository.save(countryForm);
        System.out.println("--------------------*****************END***************-----------------------------");
        List<Country> countries = countryRepository.findAll();
        modelMap.addAttribute("countries", countries);

        return "country/index";
    }

    @GetMapping("/country/edit/{countryId}")
    public String getEdit(@PathVariable Long countryId, ModelMap modelMap) {
        country = countryRepository.findById(countryId);
        modelMap.addAttribute("countryForm", country);
        System.out.println("------------------EDIT COUNTRY----------------------");
        System.out.println("Id - " + countryId);
        System.out.println(country);
        System.out.println("-------------------EDIT------------------------------");

        return "country/edit";
    }

    @PostMapping("/country/edit")
    public String postEdit(@ModelAttribute("countryForm")Country countryForm, ModelMap modelMap) {
        System.out.println("--------------------********************************-----------------------------");
        System.out.println(country);
        System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        System.out.println(countryForm);

        countryForm.setId(country.get().getId());
        countryForm.setCreated_at(country.get().getCreated_at());
        countryRepository.save(countryForm);
        System.out.println("--------------------********************************-----------------------------");

        return "redirect:/country";
    }



    @GetMapping("/country/delete/{countryId}")
    public String deleteCountry(@PathVariable Long countryId, ModelMap modelMap) {
        countryRepository.deleteById(countryId);
        List<Country> countries = countryRepository.findAll();
        modelMap.addAttribute("countries", countries);
        return "country/index";
    }
}
