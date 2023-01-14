package PecuniaSpring.controllers.viewControllers;

import PecuniaSpring.models.clasy.Kontynent;
import PecuniaSpring.models.dto.continent.ContinentDto;
import PecuniaSpring.models.dto.country.CountryDto;
import PecuniaSpring.models.dto.country.CountryDtoForm;
import PecuniaSpring.models.Continent;
import PecuniaSpring.models.Country;
import PecuniaSpring.models.repositories.ContinentRepository;
import PecuniaSpring.models.repositories.CountryRepository;
import PecuniaSpring.services.continentServices.ContinentServiceImpl;
import PecuniaSpring.services.countryServices.CountryServiceImpl;
import lombok.AllArgsConstructor;
import org.json.JSONArray;
import org.json.JSONObject;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import utils.JsonUtils;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.validation.Valid;
import java.lang.reflect.Field;
import java.sql.Array;
import java.util.*;

@AllArgsConstructor
@Controller
public class CountryController {

    private CountryRepository countryRepository;
    private CountryServiceImpl countryService;
    private ContinentServiceImpl continentService;
    private ContinentRepository continentRepository;
    private Optional<Country> countryTemp;
    private EntityManager entityManager;

    //    @GetMapping("/country")
//    public String getIndex(ModelMap modelMap)
//    {
////        List<Country> countries = countryRepository.findAll();
//        List<Country> countries = countryRepository.countriesOrderByCountry_enAsc();
//        modelMap.addAttribute("countries", countries);
//        return "country/index";
//    }
    @GetMapping("/country")
    public String getIndex(ModelMap modelMap) throws NoSuchFieldException {
        List<Country> countries = countryRepository.findAll();

        List<Object[]> objects = countryRepository.countryByContinent();
//        for (Object[] objects1 : objects) {
//            System.out.println(objects1);
//        }
        JSONArray jsonArray7 = new JSONArray(objects.toArray());
        System.out.println(jsonArray7);
        System.out.println("----------------------------------------");
//        System.out.println(JsonUtils.gsonPretty(objects));
        List<Kontynent> kontynents = new ArrayList<>();
        for (Object[] object : objects) {
            System.out.println(object[0].toString());
            kontynents.add(new ModelMapper().map(object[0], Kontynent.class));

        }

        System.out.println(kontynents);
        System.out.println(kontynents.get(0).toString());

        JSONArray jsonArray8 = new JSONArray(kontynents);
        System.out.println(jsonArray8);
        System.out.println(JsonUtils.gsonPretty(kontynents));

        //TODO Zoptymalizować powyższy kod do akceptowalnego poziomu
//        Object json = JsonUtils.gsonPretty(objects);
//        System.out.println(json);
//
//        System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++");
////        Query query = entityManager.createNativeQuery("SELECT COUNT(*) FROM Countries WHERE continent_id =:id");
////        query.setParameter("id", 1);
////        int count = ((Number) query.getSingleResult()).intValue();
////        Query query = entityManager.createNativeQuery("SELECT continent, COUNT(continent) FROM Countries GROUP BY continent");
//        List<Object> list1 = entityManager.createNativeQuery("SELECT * FROM Countries WHERE continent = ?1")
//                .setParameter(1, "Azja")
//                .getResultList();
//
//        JSONArray jsonArray = new JSONArray(list1);
//        System.out.println(jsonArray.get(0));
//        System.out.println(list1.size());
//
//        List<Object[]> objectList =  entityManager.createNativeQuery("SELECT continent as kontynent, COUNT(continent) as total FROM Countries GROUP BY continent")
//                .getResultList();
//        JSONArray jsonArray1 = new JSONArray(objectList);
//        System.out.println(jsonArray1.get(0));
//        for (Object[] objects1 : objectList) {
//            System.out.println(objects1[0] + " - " + objects1[1]);
//        }
//
////        List<Object> objectList4 = (List<Object>) entityManager.createQuery("SELECT cou FROM Country cou WHERE cou.continent = 'Azja'")
////                .getResultList();
////        JSONArray jsonArray3 = new JSONArray(objectList4);
////        System.out.println(jsonArray3.get(0));
//
//
////        List<Country> countries1 = countryRepository.countries("Azja");
////        List<CountryDto>countryDtos = new ArrayList<>();
////        for (Country country : countries1) {
////            countryDtos.add(new ModelMapper().map(country, CountryDto.class));
////        }
////        System.out.println(jsonArray1.put(countryDtos));
////        System.out.println(list1.get(1));
//        System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++");
//
//        System.out.println("-------------------------------");
//        Query query1 = entityManager.createNativeQuery("SELECT continent, COUNT(continent) as liczba FROM Countries GROUP BY continent");
//        List<Object> list = (query1.getResultList());
//        System.out.println(list);
//
//        JSONObject jsonObject = new JSONObject();
//        jsonObject.append("liczba", list.get(0));
//
////        JSONArray jsonArray = new JSONArray();
////        jsonArray.put(list.get(0));
////        System.out.println(jsonObject);
////        System.out.println(jsonArray);

        System.out.println("---------------------------------");


     return findPaginated(1, "continent", "asc", modelMap);
    }

    @GetMapping("/country/show/{countryId}")
    public String getShow(@PathVariable Long countryId, ModelMap modelMap) {
        System.out.println("+++++++++++++++++++++TU JESTEM+++++++++++++++++++++++++++++++++++++");
        System.out.println("Id - " + countryId);
        Optional<Country> country = countryRepository.findById(countryId);
        System.out.println(country.isPresent());

        try {
            CountryDtoForm countryDto = new ModelMapper().map(countryService.getCountryById(countryId), CountryDtoForm.class);
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
        modelMap.addAttribute("countryForm", new CountryDtoForm());
        getAllContinents(modelMap);
        return "country/new";
    }

    @PostMapping("/country/new")
    public String postNew(@ModelAttribute("countryForm")
                              @Valid CountryDtoForm countryForm, BindingResult result, ModelMap modelMap) {
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
        CountryDtoForm countryDto = new ModelMapper().map(countryTemp, CountryDtoForm.class);
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
                           @Valid CountryDtoForm countryForm,
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

        List<CountryDtoForm> countryDtos = new ArrayList<>();
        for (Country country : countries) {
            countryDtos.add(new ModelMapper().map(country, CountryDtoForm.class));
        }

        modelMap.addAttribute("countries", countryDtos);
        return "country/index";
    }
}
