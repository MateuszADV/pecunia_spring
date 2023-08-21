package PecuniaSpring.controllers.apiControllers;

import PecuniaSpring.chartClass.ReportMethod;
import PecuniaSpring.chartClass.Variable;
import PecuniaSpring.models.repositories.ChartRepository;
import PecuniaSpring.models.repositories.CountryRepository;
import PecuniaSpring.services.chartServices.ChartServiceImpl;
import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.*;

@RestController
@RequestMapping(path = "/api/v1")
@AllArgsConstructor
public class ChartApiController {

    private CountryRepository countryRepository;
    private ChartServiceImpl chartService;

    public ChartRepository chartRepository;

    @GetMapping("/report")
    public ResponseEntity<Object> getReport(@RequestParam("report") String report,
                                            @RequestParam("param") Optional<String> param) {

        if (!param.isPresent()) {
            try {
                Class<?> classObj = chartRepository.getClass();
                Method printTest = classObj.getDeclaredMethod(report);
                printTest.invoke(chartRepository);
                List<Object[]> objects = (List<Object[]>) printTest.invoke(chartRepository);

                try {

                    Object object = chartService.dataToChart(report, objects);
                    return ResponseEntity.ok().body(object);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    System.out.println("Napis testowy");
                    System.out.println(e.getCause());
                    return ResponseEntity.ok().body(e.getMessage() + "\n" + e.getCause() + "\nNApis testowy");
                }
            } catch (InvocationTargetException e) {
                System.out.println(e.getMessage());
                System.out.println(" catch 01");
                return ResponseEntity.ok().body(e.getCause());
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
                System.out.println(" catch 02");
                return ResponseEntity.ok().body(e.getCause());
            } catch (Exception e) {
                System.out.println(e.getMessage());
                System.out.println(" catch 03");
                return ResponseEntity.ok().body(e.getMessage() + "\ncatch 03");
            }
        } else {
            try {
                Class<?> classObj = chartRepository.getClass();
                Method printTest = classObj.getDeclaredMethod(report, String.class);
                printTest.invoke(chartRepository, param.get());
                List<Object[]> objects = (List<Object[]>) printTest.invoke(chartRepository, param.get());

                try {
                    Object object = chartService.dataToChart(report, objects);
                    return ResponseEntity.ok().body(object);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    System.out.println("Napis testowy");
                    System.out.println(e.getCause());
                    return ResponseEntity.ok().body(e.getMessage() + "\n" + e.getCause() + "\nNApis testowy");
                }

            } catch (InvocationTargetException e) {
                System.out.println(e.getMessage());
                System.out.println(" catch 04");
                return ResponseEntity.ok().body(e.getCause());
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
                System.out.println(" catch 05");
                return ResponseEntity.ok().body(e.getCause());
            } catch (Exception e) {
                System.out.println(e.getMessage());
                System.out.println(" catch 06");
                return ResponseEntity.ok().body(e.getMessage() + "\ncatch 03");
            }
        }

    }

    @GetMapping("/report/method")
    public ResponseEntity<Object> getReportMethod() {
        try {
            System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
            List<ReportMethod> reportMethods = chartService.reportMethodList(ChartRepository.class);
            System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
            return ResponseEntity.ok().body(reportMethods);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.ok().body(e.getMessage());
        }


    }
}
