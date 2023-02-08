package PecuniaSpring.controllers.viewControllers;

import PecuniaSpring.models.Making;
import PecuniaSpring.models.dto.making.MakingDto;
import PecuniaSpring.models.dto.making.MakingDtoForm;
import PecuniaSpring.models.repositories.MakingRepository;
import PecuniaSpring.services.makingServices.MakingServiceImpl;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@AllArgsConstructor
//@NoArgsConstructor
public class MakingController {

    private MakingServiceImpl makingService;
    private MakingRepository makingRepository;
    private Optional<Making> makingTmp;

    @GetMapping("/making")
    public String getIndex(ModelMap modelMap) {
        List<Making> makings = makingService.getAllMakings();
        List<MakingDto> makingDtos = new ArrayList<>();
        for (Making making : makings) {
            makingDtos.add(new ModelMapper().map(making, MakingDto.class));
        }

        modelMap.addAttribute("makingDtos", makingDtos);
        return "seting/making/index";
    }

    @GetMapping("/making/new")
    public String getNew(ModelMap modelMap) {
        modelMap.addAttribute("makingForm", new MakingDtoForm());

        return "seting/making/new";
    }

    @PostMapping("/making/new")
    public String postNew(@ModelAttribute("makingForm") MakingDtoForm makingDtoForm ) {
        Making making = new ModelMapper().map(makingDtoForm, Making.class);

        makingService.saveMaking(making);
        return "redirect:/making";
    }

    @GetMapping("/making/edit/{makingId}")
    public String getEdit(@PathVariable Long makingId, ModelMap modelMap ) {
       try {
           makingTmp = Optional.ofNullable(makingService.getMakingById(makingId));
           MakingDtoForm makingDtoForm = new ModelMapper().map(makingTmp, MakingDtoForm.class);
           modelMap.addAttribute("makingForm", makingDtoForm);
           return "seting/making/edit";
       } catch (Exception e){
           modelMap.addAttribute("error", e.getMessage());
           return "error";
       }
    }

    @PostMapping("/making/edit")
    public String postEdit(@ModelAttribute("makingForm") MakingDtoForm makingDtoForm) {
        makingDtoForm.setId(makingTmp.get().getId());
        makingDtoForm.setCreated_at(makingTmp.get().getCreated_at());
        Making making = new ModelMapper().map(makingDtoForm, Making.class);
        makingService.saveMaking(making);

        return "redirect:/making";
    }

    @GetMapping("/making/delete/{makingId}")
    public String getDelete(@PathVariable Long makingId) {
        makingService.deleteMakingById(makingId);
        return "redirect:/making";
    }
}