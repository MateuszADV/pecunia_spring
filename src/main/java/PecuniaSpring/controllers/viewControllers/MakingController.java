package PecuniaSpring.controllers.viewControllers;

import PecuniaSpring.models.Making;
import PecuniaSpring.models.dto.making.MakingDto;
import PecuniaSpring.models.dto.making.MakingDtoForm;
import PecuniaSpring.services.makingServices.MakingServiceImpl;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@AllArgsConstructor
public class MakingController {

    private MakingServiceImpl makingService;

    @GetMapping("/making")
    public String getIndex(ModelMap modelMap) {
        List<Making> makings = makingService.getAllMakings();
        List<MakingDto> makingDtos = new ArrayList<>();
        for (Making making : makings) {
            makingDtos.add(new ModelMapper().map(making, MakingDto.class));
        }

        System.out.println(makingDtos);
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
}
