package PecuniaSpring.services.chartServices;

import PecuniaSpring.chartClass.DeclaredMethod;
import PecuniaSpring.chartClass.ReportMethod;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface ChartService {

    Map<String, Object> getYamlToObj (String reportName);
    List<DeclaredMethod> declaredMethodList (Class c);
    List<ReportMethod> reportMethodList(Class c);

    Object dataToChart(String report, List<Object[]> data);

}
