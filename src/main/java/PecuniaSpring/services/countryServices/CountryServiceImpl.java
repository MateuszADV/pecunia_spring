package PecuniaSpring.services.countryServices;

import PecuniaSpring.models.Country;
import PecuniaSpring.models.repsitories.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CountryServiceImpl implements CountryService {

    @Autowired
    private CountryRepository countryRepository;

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
        Country country = null;
        if (optional.isPresent()) {
            country = optional.get();
        }else {
            throw new RuntimeException("Contry Not Found For Id :: " + id);
        }
        return country;
    }

    @Override
    public void deleteCountryById(Long id) {
        this.countryRepository.deleteById(id);
    }

    @Override
    public Page<Country> findPaginated(Integer pageNo, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
        return this.countryRepository.findAll(pageable);
    }
}
