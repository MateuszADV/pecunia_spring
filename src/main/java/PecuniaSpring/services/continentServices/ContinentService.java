package PecuniaSpring.services.continentServices;

import PecuniaSpring.models.Continent;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ContinentService {
    List<Continent> getAllContinent();
    void saveContinent(Continent continent);
    Continent getContinentById(Long id);
    void deleteContinentById(Long id);
}
