package PecuniaSpring.controllers.apiControllers;

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
import utils.JsonUtils;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
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
                                            @RequestParam("param") Optional<String> param) throws Exception {

//        chartRepository.testOne();
//        chartRepository.my_report_continents_test();
//        chartRepository.my_report_note_currency_country("England");


//        Method printTest = classObj.getDeclaredMethod(report, null);
//        System.out.println("7777777777777777777777777777777777777777777");
//        try {
////        Class<ChartRepository> c = ChartRepository.class;
//            Class c = ChartRepository.class;
////        Method[] methods = c.getMethods();
//            Method[] methods = c.getDeclaredMethods();
//            for (Method method : methods) {
//                System.out.println(method.getName() + " == " + method.getParameters().length);
//                if (method.getParameters().length > 0) {
//                    System.out.println((method.getParameters()[0]).getType().getName());
//                    System.out.println((method.getParameters()[0]).getName());
//                }
////                TypeVariable[] tp = method.getTypeParameters();
////                for (TypeVariable t : tp) {
////                    System.out.println("Type variable for Method Name "
////                            + method.getName() + " is "
////                            + t.getName());
////                }
//            }
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//            return ResponseEntity.ok().body(e.getMessage());
//        }
//
//        System.out.println("7777777777777777777777777777777777777777777");

        if (!param.isPresent()) {
            try {
                Class<?> classObj = chartRepository.getClass();
                Method printTest = classObj.getDeclaredMethod(report);
                printTest.invoke(chartRepository);
                /**
                 * Dane pobrane z bazy danych
                 */
                //TODO przerobic na funkcje któta pobiera paramety i zwraca object z wartościami do wyswietlenia na wykresie dane jedno lub więcej wartościowe
//        List<Object[]> objects = countryRepository.reportCountCountry();
//        List<Object[]> objects = chartRepository.my_report_continents_test();
//            List<Object[]> objects = chartRepository.my_report_continents_test();
                List<Object[]> objects = (List<Object[]>) printTest.invoke(chartRepository);
                List<String> labels = new ArrayList();
                List<Integer> data = new ArrayList();
                for (Object[] object : objects) {
                    labels.add(object[0].toString());
                    data.add(Integer.valueOf(object[1].toString()));
                }

//        Yaml yaml = new Yaml();
//        InputStream inputStream = this.getClass()
//                .getClassLoader()
//                .getResourceAsStream("static/reportsChart/reports/" + report + ".yml");
//        Map<String, Object> obj = yaml.load(inputStream);

                Map<String, Object> mapObject = chartService.getYamlToObj(report);
                JSONObject jsonObject = new JSONObject(mapObject);

                try {

                    /**
                     * Dodawanie danych z bazy do wyświetlenia na wykresie
                     **/
                    //TODO dodać funkcje która zwraca object z już dodanymi wartościami do wyswietlenia
                    jsonObject.getJSONObject("chart").put("labels", labels);
                    jsonObject.getJSONObject("chart").getJSONObject("datasets").put("data", data);

                    Object object = new Gson().fromJson(String.valueOf(jsonObject), Object.class);
//                    System.out.println(JsonUtils.gsonPretty(object));
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
                List<String> labels = new ArrayList();
                List<Integer> data = new ArrayList();
                for (Object[] object : objects) {
                    labels.add(object[0].toString());
                    data.add(Integer.valueOf(object[1].toString()));
                }

//        Yaml yaml = new Yaml();
//        InputStream inputStream = this.getClass()
//                .getClassLoader()
//                .getResourceAsStream("static/reportsChart/reports/" + report + ".yml");
//        Map<String, Object> obj = yaml.load(inputStream);

                Map<String, Object> mapObject = chartService.getYamlToObj(report);
                JSONObject jsonObject = new JSONObject(mapObject);

                try {

                    /**
                     * Dodawanie danych z bazy do wyświetlenia na wykresie
                     **/
                    //TODO dodać funkcje która zwraca object z już dodanymi wartościami do wyswietlenia
                    jsonObject.getJSONObject("chart").put("labels", labels);
                    jsonObject.getJSONObject("chart").getJSONObject("datasets").put("data", data);

                    Object object = new Gson().fromJson(String.valueOf(jsonObject), Object.class);
//                    System.out.println(JsonUtils.gsonPretty(object));
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
        }

    }


    @GetMapping("/reportTest")
    public ResponseEntity<Object> getReportTest(
                                                HttpServletRequest request,
                                                @RequestParam("report") String report,
                                                @RequestParam("param") Optional<String> param
                                                ) throws Exception {
        System.out.println(report);
        System.out.println(param);
        System.out.println(request.getRequestURI());
        System.out.println(request.getPathInfo());
        System.out.println(param.isPresent());
        if (!param.isPresent()) {
            return ResponseEntity.ok().body("Brak podanego parametru 'param'");
        } else {

//        System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
//        try {
////        Class<ChartRepository> c = ChartRepository.class;
//            Class c = ChartRepository.class;
////        Method[] methods = c.getMethods();
//            Method[] methods = c.getDeclaredMethods();
//            System.out.printf("- %-40s %-20s %-30s %-20s%n", "Report Name", "quality param", "Type Param", "Name Param");
//            for (Method method : methods) {
////                System.out.println(method.getName() + " == " + method.getParameters().length);
//                if (method.getParameters().length > 0) {
////                    System.out.println((method.getParameters()[0]).getType().getName());
////                    System.out.println((method.getParameters()[0]).getName());
//
//                    System.out.printf("- %-40s %-20s %-30s %-20s %n", method.getName(), method.getParameters().length, (method.getParameters()[0]).getType().getName() , (method.getParameters()[0]).getName());
//                }
//                else {
//                    System.out.printf("- %-40s %-20s %n", method.getName(), method.getParameters().length );
//                }
//            }
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//            return ResponseEntity.ok().body(e.getMessage());
//        }
//        System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");

            try {
                Class<?> classObj = chartRepository.getClass();
                Method printTest = classObj.getDeclaredMethod(report, String.class);
                printTest.invoke(chartRepository, param.get());
                List<Object[]> objects = (List<Object[]>) printTest.invoke(chartRepository, param.get());
                List<String> labels = new ArrayList();
                List<Integer> data = new ArrayList();
                for (Object[] object : objects) {
                    labels.add(object[0].toString());
                    data.add(Integer.valueOf(object[1].toString()));
                }

//        Yaml yaml = new Yaml();
//        InputStream inputStream = this.getClass()
//                .getClassLoader()
//                .getResourceAsStream("static/reportsChart/reports/" + report + ".yml");
//        Map<String, Object> obj = yaml.load(inputStream);

                Map<String, Object> mapObject = chartService.getYamlToObj(report);
                JSONObject jsonObject = new JSONObject(mapObject);

                try {

                    /**
                     * Dodawanie danych z bazy do wyświetlenia na wykresie
                     **/
                    //TODO dodać funkcje która zwraca object z już dodanymi wartościami do wyswietlenia
                    jsonObject.getJSONObject("chart").put("labels", labels);
                    jsonObject.getJSONObject("chart").getJSONObject("datasets").put("data", data);

                    Object object = new Gson().fromJson(String.valueOf(jsonObject), Object.class);
//                    System.out.println(JsonUtils.gsonPretty(object));
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
        }
//        return ResponseEntity.ok().body(report);
    }
}
