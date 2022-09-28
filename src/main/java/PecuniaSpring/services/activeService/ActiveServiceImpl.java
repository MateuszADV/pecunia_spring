package PecuniaSpring.services.activeService;

import PecuniaSpring.models.Active;
import PecuniaSpring.models.repositories.ActiveRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class ActiveServiceImpl implements ActiveService {

    private ActiveRepository activeRepository;

    @Override
    public List<Active> getAllActive() {
        return activeRepository.findAll();
    }

    @Override
    public void saveActive(Active active) {
        this.activeRepository.save(active);
    }

    @Override
    public Active getActiveById(Long id) {
        Optional<Active> active = activeRepository.findById(id);
        if (active.isPresent()) {
            return active.get();
        }else {
            throw new RuntimeException("Continent Not Found For Id :: " + id);
        }
    }

    @Override
    public void deleteActiveById(Long id) {
        this.activeRepository.deleteById(id);
    }
}
