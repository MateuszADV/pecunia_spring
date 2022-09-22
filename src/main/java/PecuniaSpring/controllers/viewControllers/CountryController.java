package PecuniaSpring.controllers.viewControllers;

import PecuniaSpring.controllers.dto.continent.ContinentDto;
import PecuniaSpring.controllers.dto.country.CountryDto;
import PecuniaSpring.models.Continent;
import PecuniaSpring.models.Country;
import PecuniaSpring.models.repositories.ContinentRepository;
import PecuniaSpring.models.repositories.CountryRepository;
import PecuniaSpring.services.continentServices.ContinentServiceImpl;
import PecuniaSpring.services.countryServices.CountryServiceImpl;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import utils.JsonUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Controller
public class CountryController {

    private CountryRepository countryRepository;
    private CountryServiceImpl countryService;
    private ContinentServiceImpl continentService;
    private ContinentRepository continentRepository;
    private Optional<Country> country;

//    @GetMapping("/country")
//    public String getIndex(ModelMap modelMap)
//    {
////        List<Country> countries = countryRepository.findAll();
//        List<Country> countries = countryRepository.countriesOrderByCountry_enAsc();
//        modelMap.addAttribute("countries", countries);
//        return "country/index";
//    }
    @GetMapping("/country")
    public String getIndex(ModelMap modelMap) {
        List<Country> countries = countryRepository.findAll();
//        System.out.println(countries);
     return findPaginated(1, "continent", "asc", modelMap);
    }

    @GetMapping("/country/show/{countryId}")
    public String getShow(@PathVariable Long countryId, ModelMap modelMap) {
        System.out.println("+++++++++++++++++++++TU JESTEM+++++++++++++++++++++++++++++++++++++");
        System.out.println("Id - " + countryId);
        Optional<Country> country = countryRepository.findById(countryId);
        System.out.println(country.isPresent());

        try {
            CountryDto countryDto = new ModelMapper().map(countryService.getCountryById(countryId), CountryDto.class);
            System.out.println(countryDto);
            String json = JsonUtils.gsonPretty(countryDto);
            System.out.println("=======================================");
            System.out.println(json);
            System.out.println("=======================================");
            modelMap.addAttribute("json", json);
            modelMap.addAttribute("country", countryDto);
        } catch (Exception e) {
            System.out.println("+-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=+");
            System.out.println(e.getMessage());
            System.out.println("+-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=+");
            modelMap.addAttribute("error", e.getMessage());
            return "error";
        }
        return "country/show";

    }

    @GetMapping("/country/new")
    public String getNew(ModelMap modelMap) {
        modelMap.addAttribute("countryForm", new CountryDto());
        List<Continent> continents = continentService.getAllContinent();
        List<ContinentDto> continentDtos = new ArrayList<>();
        for (Continent continent : continents) {
            continentDtos.add(new ModelMapper().map(continent, ContinentDto.class));
        }
        modelMap.addAttribute("continents", continentDtos);
        return "country/new";
    }

    @PostMapping("/country/new")
    public String postNew(@ModelAttribute("countryForm")CountryDto countryForm, ModelMap modelMap) {
        System.out.println("--------------------***************START*****************-----------------------------");
//        System.out.println(country.toString());
        System.out.println(JsonUtils.gsonPretty(countryForm));
        Country country1 = new ModelMapper().map(countryForm, Country.class);
        Optional<Continent> continent = continentRepository.findById(countryForm.getContinent_id());
        country1.setContinents(continent.get());
        countryService.saveCountry(country1);
        System.out.println("--------------------*****************END***************-----------------------------");
        List<Country> countries = countryRepository.findAll();
        modelMap.addAttribute("countries", countries);

//        return "country/index";
        return findPaginated(1, "continent", "asc", modelMap);
    }

    @GetMapping("/country/edit/{countryId}")
    public String getEdit(@PathVariable Long countryId, ModelMap modelMap) {
        country = countryRepository.findById(countryId);
        List<Continent> continents = continentService.getAllContinent();
        List<ContinentDto> continentDtos = new ArrayList<>();
        for (Continent continent : continents) {
            continentDtos.add(new ModelMapper().map(continent, ContinentDto.class));
        }
        System.out.println(continentDtos);
        modelMap.addAttribute("continents", continentDtos);
        CountryDto countryDto = new ModelMapper().map(country, CountryDto.class);

        countryDto.setContinent_id(country.get().getContinents().getId());
        Continent continent = country.get().getContinents();
        modelMap.addAttribute("continentEdit", continent);
        modelMap.addAttribute("countryForm", countryDto);
        System.out.println("------------------EDIT COUNTRY----------------------");
        System.out.println(country.get().getContinents().getId());
        System.out.println("Id - " + countryId);
        System.out.println(countryDto);
        System.out.println("-------------------EDIT------------------------------");

        return "country/edit";
    }

    @PostMapping("/country/edit")
    public String postEdit(@ModelAttribute("countryForm")CountryDto countryForm, ModelMap modelMap) {
        countryForm.setId(country.get().getId());
        countryForm.setCreated_at(country.get().getCreated_at());
        System.out.println("------------TU JESTEM--------------------------");
        System.out.println(countryForm);
        System.out.println(countryForm.getContinent_id());
        Country country1 = new ModelMapper().map(countryForm, Country.class);
//        System.out.println(country1.getContinents().getId());
        Optional<Continent> continent = continentRepository.findById(countryForm.getContinent_id());
        System.out.println();
        System.out.println("----------KONIEC TU JESTEM---------------------------");
        country1.setContinents(continent.get());
        countryRepository.save(country1);

        return "redirect:/country";
    }

    @GetMapping("/country/delete/{countryId}")
    public String deleteCountry(@PathVariable Long countryId, ModelMap modelMap) {
//        countryRepository.deleteById(countryId);
//        List<Country> countries = countryRepository.findAll();
//        modelMap.addAttribute("countries", countries);
//        return "country/index";

        try {
            countryService.deleteCountryById(countryId);
        } catch (Exception e) {
            System.out.println("*&*&*&*&*&*&*&*&*&*&*&*&*&*&*&*&*&*&");
            System.out.println(e.getMessage());
            System.out.println("*&*&*&*&*&*&*&*&*&*&*&*&*&*&*&*&*&*&");
            modelMap.addAttribute("error", "Country Not Found For Id :: " + countryId);
            return "error";
        }
        return findPaginated(1, "continent", "asc", modelMap);
    }

    @GetMapping("/page/{pageNo}")
    public String findPaginated(@PathVariable(value = "pageNo") int pageNo,
                                @RequestParam("sortField") String sortField,
                                @RequestParam("sortDir") String sortDir,
                                ModelMap modelMap) {
        int pageSize = 10;
        modelMap.addAttribute("pageSize", pageSize);
        String pathPage = "/page/";
        modelMap.addAttribute("pathPage", pathPage);
        System.out.println("=========================================");
        System.out.println(pageNo);
        System.out.println(sortField);
        System.out.println(sortDir);
        System.out.println("==========================================");
        Page<Country> page = countryService.findPaginated(pageNo, pageSize, sortField, sortDir);
        List<Country> countries = page.getContent();

        modelMap.addAttribute("currentPage", pageNo);
        modelMap.addAttribute("totalPages", page.getTotalPages());
        modelMap.addAttribute("totalItems", page.getTotalElements());

        modelMap.addAttribute("sortField", sortField);
        modelMap.addAttribute("sortDir",sortDir);
        modelMap.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");

        modelMap.addAttribute("countries", countries);


        return "country/index";
    }
}
