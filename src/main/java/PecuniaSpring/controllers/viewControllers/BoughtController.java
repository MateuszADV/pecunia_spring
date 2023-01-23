package PecuniaSpring.controllers.viewControllers;

import PecuniaSpring.models.Bought;
import PecuniaSpring.models.dto.bought.BoughtDto;
import PecuniaSpring.models.dto.bought.BoughtFormDto;
import PecuniaSpring.services.boughtServices.BoughtServicesImpl;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
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
public class BoughtController {

    private BoughtServicesImpl boughtServices;

    @GetMapping("/bought")
    public String getIndex(ModelMap modelMap) {
        List<Bought> boughts = boughtServices.getAllOrderById();
        List<BoughtDto> boughtDtos = new ArrayList();
        for (Bought bought : boughts) {
            boughtDtos.add(new ModelMapper().map(bought, BoughtDto.class));
        }

        modelMap.addAttribute("boughtDtos", boughtDtos);
        return "bought/index";
    }

    @GetMapping("/bought/new")
    public String getNew(ModelMap modelMap) {
        modelMap.addAttribute("boughtForm", new BoughtFormDto());
        return "bought/new";
    }

    @PostMapping("/bought/new")
    public String getNew(@ModelAttribute("boughtForm")@Valid BoughtFormDto boughtFormDto, BindingResult result, ModelMap modelMap) {
        System.out.println(JsonUtils.gsonPretty(boughtFormDto));
        Bought bought = new ModelMapper().map(boughtFormDto, Bought.class);
//        Boolean test = activeService.activeCodExist(activeDto.getActiveCod());
        if (result.hasErrors()) {
//            if (test) {
//                modelMap.addAttribute("checkActive", test);
//            }
            return "bought/new";
        }
//        if (test) {
//            modelMap.addAttribute("checkActive", test);
//            return "active/new";
//        }
        boughtServices.saveBought(bought);
        return "redirect:/bought";
    }
}
