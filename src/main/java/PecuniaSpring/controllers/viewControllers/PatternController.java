package PecuniaSpring.controllers.viewControllers;

import PecuniaSpring.models.Pattern;
import PecuniaSpring.models.dto.pattern.PatternDto;
import PecuniaSpring.services.patternService.PatternServiceImpl;
import groovy.lang.GrabExclude;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
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
    public String postNew(@ModelAttribute("patternForm")@Valid PatternDto patternDto, BindingResult result, ModelMap modelMap) {
        if(result.hasErrors()) {
            return "seting/pattern/new";
        }
        Pattern pattern = new ModelMapper().map(patternDto, Pattern.class);
        System.out.println("--------------------------------------");
        System.out.println(JsonUtils.gsonPretty(patternDto));
        System.out.println("---------------------------------------");
        Pattern patternGet = patternService.savePatternGet(pattern);
        System.out.println(JsonUtils.gsonPretty(patternGet));
        System.out.println("))))))))))))))))))))))))))((((((((((((((((((((((((((((");

//        return "redirect:/pattern";
        return getShow(pattern.getId(), modelMap);
    }

    @GetMapping("/pattern/show/{patternId}")
    public String getShow(@PathVariable Long patternId, ModelMap modelMap) {
        Pattern pattern = patternService.getPatternById(patternId);
        PatternDto patternDto = new ModelMapper().map(pattern, PatternDto.class);
        modelMap.addAttribute("pattern", patternDto);
        return "seting/pattern/show";
    }

    @GetMapping("/pattern/delete/{patternId}")
    public String deletePattern(@PathVariable Long patternId, ModelMap modelMap) {
        patternService.deletePatternById(patternId);
        return "redirect:/pattern";
    }
}
