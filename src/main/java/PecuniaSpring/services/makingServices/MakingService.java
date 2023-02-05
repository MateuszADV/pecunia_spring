package PecuniaSpring.services.makingServices;

import PecuniaSpring.models.Making;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface MakingService {
    List<Making> getAllMakings();
    void saveMaking(Making making);
    Making getMakingById(Long id);
    void deleteMakingById(Long id);
}
