package PecuniaSpring.controllers.viewControllers;

import PecuniaSpring.models.Quality;
import PecuniaSpring.models.dto.quality.QualityDto;
import PecuniaSpring.models.dto.quality.QualityDtoForm;
import PecuniaSpring.models.repositories.QualityRepository;
import PecuniaSpring.services.quality.QualityServiceImpl;
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
public class QualityController {

    private QualityServiceImpl qualityService;
    private QualityRepository qualityRepository;

    @GetMapping("/quality")
    public String getIndex(ModelMap modelMap) {
        try {
            List<Quality> qualities = qualityRepository.findAll();
            System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
            System.out.println(qualities);
            System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
            List<QualityDto> qualityDtos = new ArrayList<>();
            for (Quality quality : qualities) {
                qualityDtos.add(new ModelMapper().map(quality, QualityDto.class));
            }

            modelMap.addAttribute("qualityDtos", qualityDtos);
            return "seting/quality/index";

        } catch (Exception e){
            System.out.println(e.getMessage());
            modelMap.addAttribute("qualityDtos", null);
            return "seting/quality/index";
        }
    }

    @GetMapping("/quality/new")
    public String getNew(ModelMap modelMap) {
        modelMap.addAttribute("qualityForm", new QualityDtoForm());
        return "seting/quality/new";
    }

    @PostMapping("/quality/new")
    public String postNew(@ModelAttribute("qualityForm")@Valid QualityDtoForm qualityDtoForm, BindingResult result) {
        System.out.println(JsonUtils.gsonPretty(qualityDtoForm));
        Quality quality = new ModelMapper().map(qualityDtoForm, Quality.class);

        if (result.hasErrors()) {

            return "seting/quality/new";
        }
        qualityService.saveQuality(quality);
        return "redirect:/quality";
    }

}
