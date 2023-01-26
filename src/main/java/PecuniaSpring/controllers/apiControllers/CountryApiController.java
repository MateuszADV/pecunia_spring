package PecuniaSpring.controllers.apiControllers;

import PecuniaSpring.models.dto.country.CountryDtoForm;
import PecuniaSpring.models.Country;
import PecuniaSpring.models.repositories.CountryRepository;
import PecuniaSpring.models.sqlClass.Continent;
import PecuniaSpring.services.countryServices.CountryServiceImpl;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/api/v1")
@AllArgsConstructor
public class CountryApiController {

    private CountryRepository countryRepository;
    private CountryServiceImpl countryService;

    @GetMapping("/country")

    public ResponseEntity<Object> getAllCountry() {
        List<Country> countries = countryRepository.findAll();
        List<CountryDtoForm> countryDtoList = new ArrayList<>();
        for (Country country : countries) {
            countryDtoList.add(new ModelMapper().map(country, CountryDtoForm.class));
        }
        return ResponseEntity.ok().body(countryDtoList);
    }

    @PostMapping("/country")
    public ResponseEntity<Object> getCountryById(@RequestParam String countryId) {
        try {
            Optional<Country> country = countryRepository.findById(Long.parseLong(countryId));
            CountryDtoForm countryDto = new ModelMapper().map(country.get(), CountryDtoForm.class);
            return ResponseEntity.ok().body(countryDto);
        }
        catch (Exception e) {
            return ResponseEntity.ok().body(e.getMessage());
        }
    }

    @GetMapping("/count_country")
    public ResponseEntity<List<Continent>> getCountry() {

        return ResponseEntity.ok().body(countryService.getTotalCountry());
    }

}