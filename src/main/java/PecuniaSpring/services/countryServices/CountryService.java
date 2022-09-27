package PecuniaSpring.services.countryServices;

import PecuniaSpring.models.Country;
import org.springframework.data.domain.Page;

import java.util.List;


public interface CountryService {
    List<Country> getAllCountries();
    void saveCountry(Country country);
    Country getCountryById(Long id);
    void deleteCountryById(Long id);
    Page<Country> findPaginated(Integer pageNo, Integer pageSize, String sortField, String sortDirection);
    List<Country> getCountriesWithContinent(String continentEn);
    List<Country> getCountryByCountryEnAsc();
}
