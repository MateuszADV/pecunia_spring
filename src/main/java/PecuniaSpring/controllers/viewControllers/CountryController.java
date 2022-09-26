package PecuniaSpring.controllers.viewControllers;

import PecuniaSpring.controllers.dto.continent.ContinentDto;
import PecuniaSpring.controllers.dto.country.CountryDto;
import PecuniaSpring.models.Continent;
import PecuniaSpring.models.Country;
import PecuniaSpring.models.repositories.ContinentRepository;
import PecuniaSpring.models.repositories.CountryRepository;
import PecuniaSpring.registration.RegistrationRequest;
import PecuniaSpring.services.continentServices.ContinentServiceImpl;
import PecuniaSpring.services.countryServices.CountryServiceImpl;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import utils.JsonUtils;

import javax.validation.Valid;
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
    private Optional<Country> countryTemp;

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
            System.out.println("=======================================");
            System.out.println(countryDto);
            String json = JsonUtils.gsonPretty(countryDto);

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
        getAllContinents(modelMap);
        return "country/new";
    }

    @PostMapping("/country/new")
    public String postNew(@ModelAttribute("countryForm")
                              @Valid CountryDto countryForm, BindingResult result, ModelMap modelMap) {
        System.out.println("--------------------***************START*****************-----------------------------");
        if (result.hasErrors()) {
            getAllContinents(modelMap);
            return "country/new";
        }
        System.out.println(JsonUtils.gsonPretty(countryForm));
        Country country = new ModelMapper().map(countryForm, Country.class);
        countryService.saveCountry(country);
        System.out.println("--------------------*****************END***************-----------------------------");
//        return "country/index";
        return findPaginated(1, "continent", "asc", modelMap);
    }

    @GetMapping("/country/edit/{countryId}")
    public String getEdit(@PathVariable Long countryId, ModelMap modelMap) {
        countryTemp = countryRepository.findById(countryId);
        List<Continent> continents = continentService.getAllContinent();
        List<ContinentDto> continentDtos = new ArrayList<>();
        for (Continent continent : continents) {
            continentDtos.add(new ModelMapper().map(continent, ContinentDto.class));
        }
        System.out.println(continentDtos);
        modelMap.addAttribute("continents", continentDtos);
        CountryDto countryDto = new ModelMapper().map(countryTemp, CountryDto.class);
        System.out.println("+++++++++++++++++START+++++++++++++++++++++");
        System.out.println(countryDto.getContinents().getContinentPl());
        System.out.println("++++++++++++++++STOP++++++++++++++++++++++++");

//        countryDto.setContinent_id(countryTemp.get().getContinents().getId());
        Continent continent = countryTemp.get().getContinents();
        modelMap.addAttribute("continentEdit", continent);
        modelMap.addAttribute("countryForm", countryDto);
        return "country/edit";
    }

    @PostMapping("/country/edit")
    public String postEdit(@ModelAttribute("countryForm")
                           @Valid CountryDto countryForm,
                           BindingResult result, ModelMap modelMap) {
        if (result.hasErrors()) {
            getAllContinents(modelMap);
            return "country/edit";
        }
        countryForm.setId(countryTemp.get().getId());
        countryForm.setCreated_at(countryTemp.get().getCreated_at());

        Country country = new ModelMapper().map(countryForm, Country.class);
        countryRepository.save(country);

        return "redirect:/country";
    }

    private void getAllContinents(ModelMap modelMap) {
        List<Continent> continents = continentService.getAllContinent();
        List<ContinentDto> continentDtos = new ArrayList<>();
        for (Continent continent : continents) {
            continentDtos.add(new ModelMapper().map(continent, ContinentDto.class));
        }
        modelMap.addAttribute("continents", continentDtos);
    }

    @GetMapping("/country/delete/{countryId}")
    public String deleteCountry(@PathVariable Long countryId, ModelMap modelMap) {

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

        List<CountryDto> countryDtos = new ArrayList<>();
        for (Country country : countries) {
            countryDtos.add(new ModelMapper().map(country, CountryDto.class));
        }

        modelMap.addAttribute("countries", countryDtos);
        return "country/index";
    }
}
