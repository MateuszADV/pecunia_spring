package PecuniaSpring.controllers.viewControllers;

import PecuniaSpring.models.dto.UserRegistration;
import PecuniaSpring.models.dto.country.CountryDtoForm;
import PecuniaSpring.models.Country;
import PecuniaSpring.models.other.ApiResponseInfo;
import PecuniaSpring.models.other.Exchange;
import PecuniaSpring.models.other.GetRateCurrencyTableA;
import PecuniaSpring.models.repositories.CountryRepository;
import PecuniaSpring.registration.EmailValidator;
import PecuniaSpring.registration.RegistrationRequest;
import PecuniaSpring.registration.RegistrationService;
import PecuniaSpring.security.config.UserCheckLoged;
import PecuniaSpring.services.apiService.ApiServiceImpl;
import com.sun.jersey.api.client.ClientResponse;
import lombok.AllArgsConstructor;
import org.json.JSONArray;
import org.json.JSONObject;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import utils.JsonUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@Controller
@AllArgsConstructor
public class HomeControler {

    private RegistrationService registrationService;
    private final EmailValidator emailValidator;
    private UserCheckLoged userCheckLoged;
    private CountryRepository countryRepository;

    private ApiServiceImpl apiService;

    @GetMapping("/")
    public String getIndex(ModelMap modelMap,
                           HttpServletRequest request,
                           HttpServletResponse response) throws ParseException {
        GetRateCurrencyTableA getRateCurrencyTableA = new GetRateCurrencyTableA();
        Exchange exchange = new Exchange();
        ApiResponseInfo apiResponseInfo = new ApiResponseInfo();


        ClientResponse clientResponse = apiService.clientResponse("https://api.nbp.pl/api/exchangerates/tables/A/?format=json");
        String stringJson = clientResponse.getEntity(String.class);
        JSONArray jsonArray = new JSONArray(stringJson);
//        System.out.println(clientResponse.getHeaders());
//        System.out.println(JsonUtils.gsonPretty(jsonArray.getJSONObject(0).get("table")));
//        System.out.println(JsonUtils.gsonPretty(jsonArray.getJSONObject(0).get("no")));
//        System.out.println(JsonUtils.gsonPretty(jsonArray.getJSONObject(0).get("effectiveDate")));



        String startDate = jsonArray.getJSONObject(0).get("effectiveDate").toString();
        java.sql.Date date = java.sql.Date.valueOf(startDate);
        System.out.println(date);

        apiResponseInfo.setResponseStatusInfo(clientResponse.getStatusInfo());
        apiResponseInfo.setDate(date);

        exchange.setNo(jsonArray.getJSONObject(0).get("no").toString());
        exchange.setTable(jsonArray.getJSONObject(0).get("table").toString());
        exchange.setEffectiveDate(jsonArray.getJSONObject(0).get("effectiveDate").toString());
//        getRateCurrencyTableA.getExchange().setNo(jsonArray.getJSONObject(0).get("no"));
//        getRateCurrencyTableA.getExchange().setTable(jsonArray.getJSONObject(0).get("table").toString());
//        getRateCurrencyTableA.getExchange().setEffectiveDate(jsonArray.getJSONObject(0).get("effectiveDate").toString());

        System.out.println(JsonUtils.gsonPretty(jsonArray.getJSONObject(0).getJSONArray("rates").getJSONObject(0).get("code")));
        JSONArray jsonArray1 = new JSONArray(jsonArray.getJSONObject(0).getJSONArray("rates"));

        for (int i = 0; i < jsonArray1.length(); i++) {
            System.out.println(jsonArray1.getJSONObject(i));
        }

        getRateCurrencyTableA.setExchange(exchange);
        getRateCurrencyTableA.setApiResponseInfo(apiResponseInfo);
        System.out.println(JsonUtils.gsonPretty(getRateCurrencyTableA));


        return "home/index";
    }

    @GetMapping("/about")
    public String getAbout(ModelMap modelMap){
        modelMap.addAttribute("standardDate", new Date());
        return "home/about";
    }

    @GetMapping("/registration")
    public String getRegistration(ModelMap modelMap){
        modelMap.addAttribute("userForm", new UserRegistration());
        System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
        System.out.println("TU JESTEM");
        System.out.println("*****************************************************************");
        return "home/registration";
    }

    @PostMapping("/registration")
    public String postRegistration(@ModelAttribute("userForm") @Valid RegistrationRequest request, BindingResult result, Model model){
        System.out.println("--------------------********************************-----------------------------");
//        System.out.println(registrationService.register(request));
//        Boolean isValidEmail = emailValidator.test(request.getEmail());
//        System.out.println(isValidEmail.toString());
        System.out.println("--------------------NAPIS TESTOWY--------------------------");
        System.out.println(request.toString());
        System.out.println(result.getModel().toString());
        if (result.hasErrors()) {
            return "home/registration";
        }

        try {
            System.out.println("%%%%%%%%%%%%%%%%%%%%%%TU COś POWINNO SIę DZIAć$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
            registrationService.register(request);
        }catch (Exception e) {
            System.out.println(e.fillInStackTrace());
        }
        return "redirect:/";
    }

    @GetMapping("/login")
    public String getLogin(){
        return "login";
    }

    @GetMapping("/error")
    public String getError(){
        return "error";
    }

    @GetMapping("/test")
    public String getTest(ModelMap modelMap,
                          HttpServletRequest request,
                          HttpServletResponse response){
        System.out.println("==============STRONA TESTOWA===================");
        List<Country> countries = countryRepository.findAll();
//        countries.stream().forEach(System.out::println);
        List<CountryDtoForm> countryDtos = new ArrayList<>();
        for (Country country : countries) {
            countryDtos.add(new ModelMapper().map(country, CountryDtoForm.class));
        }
        countryDtos.stream()
                .filter(country -> country.getId() > 100)
                .filter(country -> country.getId() < 150)
                .filter(country -> country.getContinent().contains("Azja"))
                .forEach(System.out::println);


//        ********************************************************
//        ***************TESTY AUTORYZACJI************************
//        ********************************************************

        System.out.println("++++++++++++++++++++++++++++NAPIS TESTOWY++++++++++++++++++++++++++++++++++");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println(userCheckLoged.UserCheckLoged().getName());
        System.out.println(userCheckLoged.UserCheckLoged().getAuthorities());
        System.out.println(userCheckLoged.UserCheckLoged().getCredentials());
        System.out.println(userCheckLoged.UserCheckLoged().getPrincipal().getClass().getCanonicalName());


        System.out.println("---------------------TEST START------------------------");
        System.out.println(request.toString());
        System.out.println(response.getLocale().toString());
        modelMap.addAttribute("ip", request.getRemoteAddr());


        System.out.println("=========================================");
        Locale currentLocale = request.getLocale();
        System.out.println(response.getLocale().getCountry() + " : " + response.getLocale().getDisplayCountry());
        System.out.println(currentLocale.getLanguage() + " : " + currentLocale.getDisplayLanguage());
//        System.out.println(request.getHeader("X-FORWARDED-FOR"));
        System.out.println(request.getRemoteAddr());
        System.out.println("==========================================");

        System.out.println(LocalDateTime.now());

        modelMap.addAttribute("standardDate", new Date());
        modelMap.addAttribute("localDateTime", LocalDateTime.now());
        modelMap.addAttribute("localDate", LocalDate.now());
        modelMap.addAttribute("timestamp", Instant.now());

        System.out.println("---------------------TEST STOP------------------------");
        return "home/test";
    }

}
