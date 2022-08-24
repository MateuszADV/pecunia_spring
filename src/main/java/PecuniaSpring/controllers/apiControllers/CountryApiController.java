package PecuniaSpring.controllers.apiControllers;

import PecuniaSpring.controllers.dto.CountryDto;
import PecuniaSpring.models.Country;
import PecuniaSpring.models.repositories.CountryRepository;
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

    @GetMapping("/country")

    public ResponseEntity<Object> getAllCountry() {
        List<Country> countries = countryRepository.findAll();
        List<CountryDto> countryDtoList = new ArrayList<>();
        for (Country country : countries) {
            countryDtoList.add(new ModelMapper().map(country, CountryDto.class));
        }
        return ResponseEntity.ok().body(countryDtoList);
    }

    @PostMapping("/country")
    public ResponseEntity<Object> getCountryById(@RequestParam String countryId) {
        try {
            Optional<Country> country = countryRepository.findById(Long.parseLong(countryId));
            CountryDto countryDto = new ModelMapper().map(country.get(), CountryDto.class);
            return ResponseEntity.ok().body(countryDto);
        }
        catch (Exception e) {
            return ResponseEntity.ok().body(e.getMessage());
        }
    }

}