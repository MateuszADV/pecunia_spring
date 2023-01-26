package PecuniaSpring.services.countryServices;

import PecuniaSpring.models.Country;
import PecuniaSpring.models.sqlClass.Continent;
import PecuniaSpring.models.sqlClass.CountryCount;
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
    List<Country> searchCountry(String keyWord);
    Country getCountyByCountryEn(String countryEn);

//    *****************************************
//    ******Query związane z countries*********
//    *****************************************
    List<CountryCount> countryCounts();

    List<Continent> getTotalCountry();
}
