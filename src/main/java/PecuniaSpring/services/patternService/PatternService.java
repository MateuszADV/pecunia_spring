package PecuniaSpring.services.patternService;

import PecuniaSpring.models.Pattern;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PatternService {
    List<Pattern> getAllPattern();
    void savePattern(Pattern pattern);
    Pattern savePatternGet(Pattern pattern);
    Pattern getPatternById(Long id);
    void deletePatternById(Long id);
}
