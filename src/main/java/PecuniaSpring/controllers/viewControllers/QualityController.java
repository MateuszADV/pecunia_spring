package PecuniaSpring.controllers.viewControllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class QualityController {

    @GetMapping("/quality")
    public String getIndex(ModelMap modelMap) {

        return "seting/quality/index";
    }
}
