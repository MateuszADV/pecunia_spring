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

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.TypeVariable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "/api/v1")
@AllArgsConstructor
public class ChartApiController {

    private CountryRepository countryRepository;
    private ChartServiceImpl chartService;

    public ChartRepository chartRepository;

    @GetMapping("/report")
    public ResponseEntity<Object> yamlToJson(@RequestParam("report") String report) throws Exception {

//        chartRepository.testOne();
//        chartRepository.my_report_continents_test();
        chartRepository.my_report_note_currency_country("England");


//        Method printTest = classObj.getDeclaredMethod(report, null);
        System.out.println("7777777777777777777777777777777777777777777");
//        Class<ChartRepository> c = ChartRepository.class;
        Class c = ChartRepository.class;
//        Method[] methods = c.getMethods();
        Method[] methods = c.getDeclaredMethods();
        for (Method method : methods) {
            System.out.println(method.getName() + " == " + method.getParameters().length);
            if (method.getParameters().length > 0) {
                System.out.println((method.getParameters()[0]).getType().getName());
                System.out.println((method.getParameters()[0]).getName());
            }
            TypeVariable[] tp = method.getTypeParameters();
            for (TypeVariable t : tp) {
                System.out.println("Type variable for Method Name "
                        + method.getName() + " is "
                        + t.getName());
            }
        }

        System.out.println("7777777777777777777777777777777777777777777");

        Class<?> classObj = chartRepository.getClass();
        Method printTest = classObj.getDeclaredMethod(report, String.class);
        try {
            printTest.invoke(chartRepository, "Poland");
        } catch (InvocationTargetException e) {
            System.out.println(e.getCause());
        }



        /**
         * Dane pobrane z bazy danych
         */
        //TODO przerobic na funkcje któta pobiera paramety i zwraca object z wartościami do wyswietlenia na wykresie dane jedno lub więcej wartościowe
//        List<Object[]> objects = countryRepository.reportCountCountry();
//        List<Object[]> objects = chartRepository.my_report_continents_test();
        List<Object[]> objects = chartRepository.my_report_note_currency_country("Poland");
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
//            System.out.println(JsonUtils.gsonPretty(object));
            return ResponseEntity.ok().body(object);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.ok().body(e.getMessage());
        }


    }
}
