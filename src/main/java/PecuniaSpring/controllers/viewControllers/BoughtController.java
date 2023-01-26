package PecuniaSpring.controllers.viewControllers;

import PecuniaSpring.models.Bought;
import PecuniaSpring.models.dto.bought.BoughtDto;
import PecuniaSpring.models.dto.bought.BoughtFormDto;
import PecuniaSpring.models.repositories.BoughtRepository;
import PecuniaSpring.services.boughtServices.BoughtServicesImpl;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import utils.JsonUtils;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@AllArgsConstructor
public class BoughtController {

    private BoughtServicesImpl boughtServices;
    private BoughtRepository boughtRepository;

    private Optional<Bought> boughtTemp;

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
    public String posNew(@ModelAttribute("boughtForm")@Valid BoughtFormDto boughtFormDto, BindingResult result, ModelMap modelMap) {
        System.out.println(JsonUtils.gsonPretty(boughtFormDto));

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
        Bought bought = new ModelMapper().map(boughtFormDto, Bought.class);
        boughtServices.saveBought(bought);
        return "redirect:/bought";
    }

    @GetMapping("/bought/edit/{boughtId}")
    public String getEdit(@PathVariable Long boughtId, ModelMap modelMap) {
        boughtTemp = Optional.ofNullable(boughtServices.getBoughtById(boughtId));
        BoughtFormDto boughtFormDto = new ModelMapper().map(boughtTemp, BoughtFormDto.class);
        modelMap.addAttribute("boughtForm", boughtFormDto);
        return "bought/edit";
    }

    @PostMapping("/bought/edit")
    public String postEdit(@ModelAttribute("boughtForm")@Valid BoughtFormDto boughtFormDto, BindingResult result, ModelMap modelMap) {
        System.out.println(JsonUtils.gsonPretty(boughtFormDto));

        if (result.hasErrors()) {
            return "bought/edit";
        }

        Bought bought = new ModelMapper().map(boughtFormDto, Bought.class);
        bought.setId(boughtTemp.get().getId());

        try {
           boughtServices.updateBought(bought);
        }catch (Exception e) {
            modelMap.addAttribute("error", e.getMessage());
            return "error";
        }

        return "redirect:/bought";
    }

    @GetMapping("/bought/delete/{id}")
    public String getDelete(@PathVariable Long id, ModelMap modelMap) {
        try {
            boughtServices.deleteBoughtById(id);
            return "redirect:/bought";
        }catch (Exception e) {
            modelMap.addAttribute("error", e.getMessage());
            return "error";
        }
    }
}
