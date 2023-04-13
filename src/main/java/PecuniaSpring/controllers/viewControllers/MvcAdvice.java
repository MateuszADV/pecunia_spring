package PecuniaSpring.controllers.viewControllers;


import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.Date;

@ControllerAdvice
public class MvcAdvice {
    @ModelAttribute
    public void addRateMetal(ModelMap modelMap) {
        modelMap.addAttribute("standardDate", new Date());
    }
}
