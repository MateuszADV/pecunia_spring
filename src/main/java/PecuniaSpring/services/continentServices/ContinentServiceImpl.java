package PecuniaSpring.services.continentServices;

import PecuniaSpring.models.Continent;
import PecuniaSpring.models.repositories.ContinentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

@Service
public class ContinentServiceImpl implements ContinentService {

    @Autowired
    private ContinentRepository continentRepository;

    @Override
    public List<Continent> getAllContinent() {
        return continentRepository.findAll();
    }

    @Override
    public void saveContinent(Continent continent) {
        this.continentRepository.save(continent);
    }

    @Override
    public Continent getContinentById(Long id) {
        Optional<Continent> optional = continentRepository.findById(id);
        Continent continent = new Continent();
        if (optional.isPresent()) {
            continent = optional.get();
        }else {
            throw new RuntimeException("Continent Not Found For Id :: " + id);
        }
        return continent;
    }

    @Override
    public void deleteContinentById(Long id) {
        this.continentRepository.deleteById(id);
    }

    @Override
    public Continent getContinentByContinentEn(String continentEn) {
        return continentRepository.getContinent(continentEn);
    }
}
