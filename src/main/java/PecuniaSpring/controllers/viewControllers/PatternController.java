package PecuniaSpring.controllers.viewControllers;

import PecuniaSpring.models.Pattern;
import PecuniaSpring.models.dto.pattern.PatternDto;
import PecuniaSpring.services.patternService.PatternServiceImpl;
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

@AllArgsConstructor
@Controller
public class PatternController {

    private PatternServiceImpl patternService;

    @GetMapping("/pattern")
    public String getIndex(ModelMap modelMap) {
        List<Pattern> patterns = patternService.getAllPattern();
        List<PatternDto> patternDtos = new ArrayList<>();
        for (Pattern pattern : patterns) {
            patternDtos.add(new ModelMapper().map(pattern, PatternDto.class));
        }
        modelMap.addAttribute("patternDtos", patternDtos);
        return "seting/pattern/index";
    }

    @GetMapping("/pattern/new")
    public String getNew(ModelMap modelMap) {
        modelMap.addAttribute("patternForm", new PatternDto());
        return "seting/pattern/new";
    }

    @PostMapping("/pattern/new")
    public String postNew(@ModelAttribute("patternForm")@Valid PatternDto patternDto, BindingResult result) {
        if(result.hasErrors()) {
            return "seting/pattern/new";
        }
        Pattern pattern = new ModelMapper().map(patternDto, Pattern.class);
        System.out.println("--------------------------------------");
        System.out.println(JsonUtils.gsonPretty(patternDto));
        System.out.println("---------------------------------------");
        patternService.savePattern(pattern);


        return "redirect:/pattern";
    }
}
