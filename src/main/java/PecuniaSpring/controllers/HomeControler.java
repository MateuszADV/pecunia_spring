package PecuniaSpring.controllers;

import PecuniaSpring.controllers.dto.UserRegistration;
import PecuniaSpring.registration.EmailValidator;
import PecuniaSpring.registration.RegistrationRequest;
import PecuniaSpring.registration.RegistrationService;
import PecuniaSpring.security.config.UserCheckLoged;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Locale;

@Controller
@AllArgsConstructor
public class HomeControler {

    private RegistrationService registrationService;
    private final EmailValidator emailValidator;
    private UserCheckLoged userCheckLoged;




    @GetMapping("/")
    public String getIndex(ModelMap modelMap,
                           HttpServletRequest request,
                           HttpServletResponse response){
        System.out.println("++++++++++++++++++++++++++++NAPIS TESTOWY++++++++++++++++++++++++++++++++++");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        System.out.println(userCheckLoged.UserCheckLoged().getName());
//        System.out.println(userCheckLoged.UserCheckLoged().getAuthorities());
//        System.out.println(userCheckLoged.UserCheckLoged().getCredentials());
//        System.out.println(userCheckLoged.UserCheckLoged().getPrincipal().getClass().getCanonicalName());

        modelMap.addAttribute("test", "NAPIS TESTOWY");

        System.out.println("---------------------TEST START------------------------");
        System.out.println(request.toString());
        System.out.println(response.getLocale().toString());


        System.out.println("=========================================");
        Locale currentLocale = request.getLocale();
        System.out.println(response.getLocale().getCountry() + " : " + response.getLocale().getDisplayCountry());
        System.out.println(currentLocale.getLanguage() + " : " + currentLocale.getDisplayLanguage());
        System.out.println(request.getHeader("X-FORWARDED-FOR"));
        System.out.println(request.getRemoteAddr());
        System.out.println("==========================================");

        System.out.println(LocalDateTime.now());

        modelMap.addAttribute("standardDate", new Date());
        modelMap.addAttribute("localDateTime", LocalDateTime.now());
        modelMap.addAttribute("localDate", LocalDate.now());
        modelMap.addAttribute("timestamp", Instant.now());

        System.out.println("---------------------TEST STOP------------------------");
        return "home/index";
    }

    @GetMapping("/about")
    public String getAbout(){
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
    public String getTest(){
        System.out.println("==============STRONA TESTOWA===================");
        return "home/test";
    }

}
