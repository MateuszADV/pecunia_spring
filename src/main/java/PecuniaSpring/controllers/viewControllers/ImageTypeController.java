package PecuniaSpring.controllers.viewControllers;

import PecuniaSpring.models.ImageType;
import PecuniaSpring.models.dto.imageType.ImageTypeDto;
import PecuniaSpring.models.dto.imageType.ImageTypeDtoForm;
import PecuniaSpring.services.imageTypeService.ImageTypeSeviceImpl;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import utils.JsonUtils;

import java.util.ArrayList;
import java.util.List;

@Controller
@AllArgsConstructor
public class ImageTypeController {

    private ImageTypeSeviceImpl imageTypeSevice;

    @GetMapping("/imageType")
    public String getIndex(ModelMap modelMap) {
        List<ImageType> imageTypes = imageTypeSevice.getAllImageTypes();
        List<ImageTypeDto> imageTypeDtos = new ArrayList<>();
        for (ImageType imageType : imageTypes) {
            imageTypeDtos.add(new ModelMapper().map(imageType, ImageTypeDto.class));
        }

        modelMap.addAttribute("imageTypeDtos", imageTypeDtos);
        return "seting/imageType/index";
    }

    @GetMapping("/imageType/new")
    public String getNew(ModelMap modelMap) {
        modelMap.addAttribute("imageTypeForm", new ImageTypeDtoForm());
        return "seting/imageType/new";
    }

    @PostMapping("/imageType/new")
    public String postNew(@ModelAttribute("imageTypeForm") ImageTypeDtoForm imageTypeDtoForm) {

        System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
        System.out.println(JsonUtils.gsonPretty(imageTypeDtoForm));
        System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");

        return "redirect:/imageType";
    }
}
