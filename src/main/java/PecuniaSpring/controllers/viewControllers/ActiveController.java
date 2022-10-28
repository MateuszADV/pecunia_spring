package PecuniaSpring.controllers.viewControllers;

import PecuniaSpring.models.dto.active.ActiveDto;
import PecuniaSpring.models.Active;
import PecuniaSpring.models.repositories.ActiveRepository;
import PecuniaSpring.services.activeService.ActiveServiceImpl;
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
public class ActiveController {

    private ActiveServiceImpl activeService;
    private ActiveRepository activeRepository;
    private Optional<Active> activeTemp;

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
        Boolean test = activeService.activeCodExist(activeDto.getActiveCod());
        if (result.hasErrors()) {
            if (test) {
                modelMap.addAttribute("checkActive", test);
            }
            return "active/new";
        }
        if (test) {
            modelMap.addAttribute("checkActive", test);
            return "active/new";
        }
        activeService.saveActive(active);
        return "redirect:/active";
    }

    @GetMapping("/active/edit/{activeId}")
    public String getEdit(@PathVariable Long activeId, ModelMap modelMap) {
        activeTemp = activeRepository.findById(activeId);
        Active active = activeService.getActiveById(activeId);
        ActiveDto activeDto = new ModelMapper().map(active, ActiveDto.class);
        modelMap.addAttribute("activeForm", activeDto);
        return "active/edit";
    }

    @PostMapping("/active/edit")
    public String postEdit(@ModelAttribute("activeForm")@Valid ActiveDto activeDto, BindingResult result, ModelMap modelMap) {
        activeDto.setId(activeTemp.get().getId());
        activeDto.setCreated_at(activeTemp.get().getCreated_at());
        if (result.hasErrors()) {
            if (activeService.getActiveByActiveCod(activeDto.getActiveCod()) != null) {
                if (activeDto.getId() != (activeService.getActiveByActiveCod(activeDto.getActiveCod()).getId())) {
                    modelMap.addAttribute("checkActive", true);
                 }
            }
            return "active/edit";
        }

        if (activeService.getActiveByActiveCod(activeDto.getActiveCod()) != null) {
            if (activeDto.getId() != (activeService.getActiveByActiveCod(activeDto.getActiveCod()).getId())) {
                modelMap.addAttribute("checkActive", true);
                return "active/edit";
            }
        }
        Active active = new ModelMapper().map(activeDto, Active.class);

        activeService.saveActive(active);
        return "redirect:/active";
    }

    @GetMapping("/active/delete/{activeId}")
    public String deleteActive(@PathVariable Long activeId, ModelMap modelMap) {

        try {
            activeService.deleteActiveById(activeId);
        } catch (Exception e) {
            System.out.println("*&*&*&*&*&*&*&*&*&*&*&*&*&*&*&*&*&*&");
            System.out.println(e.getMessage());
            System.out.println("*&*&*&*&*&*&*&*&*&*&*&*&*&*&*&*&*&*&");
            modelMap.addAttribute("error", "Active Not Found For Id :: " + activeId);
            return "error";
        }
        return "redirect:/active";
    }
}
