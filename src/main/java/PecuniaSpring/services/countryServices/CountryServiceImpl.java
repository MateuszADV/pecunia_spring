package PecuniaSpring.services.countryServices;

import PecuniaSpring.models.Continent;
import PecuniaSpring.models.Country;
import PecuniaSpring.models.repositories.ContinentRepository;
import PecuniaSpring.models.repositories.CountryRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CountryServiceImpl implements CountryService {

    @Autowired
    private CountryRepository countryRepository;
    private ContinentRepository continentRepository;

    @Override
    public List<Country> getAllCountries() {
        return countryRepository.findAll();
    }

    @Override
    public void saveCountry(Country country) {
        this.countryRepository.save(country);
    }

    @Override
    public Country getCountryById(Long id) {
        Optional<Country> optional = countryRepository.findById(id);
        Country country = new Country();
        if (optional.isPresent()) {
            country = optional.get();
        }else {
            throw new RuntimeException("Country Not Found For Id :: " + id);
        }
        return country;
    }

    @Override
    public void deleteCountryById(Long id) {
        this.countryRepository.deleteById(id);
    }

    @Override
    public Page<Country> findPaginated(Integer pageNo, Integer pageSize, String sortField, String sortDirection) {
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() :
                Sort.by(sortField).descending();
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
        return this.countryRepository.findAll(pageable);
    }

    @Override
    public List<Country> getCountriesWithContinent(String continentEn) {
        System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++");
        System.out.println(continentEn);
        Continent continent = continentRepository.getContinent(continentEn);

        List<Country> countries = countryRepository.countries(continent.getContinentPl());
        System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        return countries;
    }

    @Override
    public List<Country> getCountryByCountryEnAsc() {
        return countryRepository.countriesOrderByCountryEnAsc();
    }

    @Override
    public List<Country> searchCountry(String keyWord) {
        return countryRepository.searchCountry(keyWord);
    }
}
