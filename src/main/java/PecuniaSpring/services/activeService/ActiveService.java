package PecuniaSpring.services.activeService;

import PecuniaSpring.models.Active;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ActiveService {
    List<Active> getAllActive();
    void saveActive(Active active);
    Active getActiveById(Long id);
    void deleteActiveById(Long id);
}
