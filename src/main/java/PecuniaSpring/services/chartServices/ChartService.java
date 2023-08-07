package PecuniaSpring.services.chartServices;

import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public interface ChartService {

    Map<String, Object> getYamlToObj (String reportName);
}
