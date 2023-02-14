package PecuniaSpring.services.quality;

import PecuniaSpring.models.Quality;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface QualityService {
    List<Quality> getAllQuality();
    void saveQuality(Quality quality);
    Quality getQualityById(Long id);
    void deleteQualityById(Long id);
}
