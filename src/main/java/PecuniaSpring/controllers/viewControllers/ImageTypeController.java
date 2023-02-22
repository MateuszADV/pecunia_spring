package PecuniaSpring.controllers.viewControllers;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@AllArgsConstructor
public class ImageTypeController {

    @GetMapping("/imageType")
    public String getIndex(ModelMap modelMap) {

        return "seting/imageType/index";
    }
}
