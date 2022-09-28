package PecuniaSpring.controllers.viewControllers;

import PecuniaSpring.controllers.dto.active.ActiveDto;
import PecuniaSpring.models.Active;
import PecuniaSpring.services.activeService.ActiveServiceImpl;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import utils.JsonUtils;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
@AllArgsConstructor
public class ActiveController {

    private ActiveServiceImpl activeService;

    @GetMapping("/active")
    public String getIndex(ModelMap modelMap) {
        List<ActiveDto> activeDtos = new ArrayList<>();
        List<Active> actives = activeService.getAllActive();
        for (Active active : actives) {
            activeDtos.add(new ModelMapper().map(active, ActiveDto.class));
        }

        modelMap.addAttribute("activeDtos", activeDtos);
        return "active/index";
    }

    @GetMapping("/active/new")
    public String getNew(ModelMap modelMap) {
        modelMap.addAttribute("activeForm", new ActiveDto());
        return "active/new";
    }

    @PostMapping("/active/new")
    public String getNew(@ModelAttribute("activeForm")@Valid ActiveDto activeDto, BindingResult result, ModelMap modelMap) {
        System.out.println(JsonUtils.gsonPretty(activeDto));
        Active active = new ModelMapper().map(activeDto, Active.class);
        if (result.hasErrors()) {
            return "active/new";
        }
//        activeService.saveActive(active);

        System.out.println("-------------TEST START-----------------------");
        Boolean test = activeService.activeCodExist(activeDto.getActiveCod());
        System.out.println(test);
        System.out.println("-------------TEST stop-----------------------");
        return "redirect:/active";
    }
}
