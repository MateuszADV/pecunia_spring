package PecuniaSpring.controllers.viewControllers;

import PecuniaSpring.models.ImageType;
import PecuniaSpring.models.dto.imageType.ImageTypeDto;
import PecuniaSpring.models.dto.imageType.ImageTypeDtoForm;
import PecuniaSpring.services.imageTypeService.ImageTypeSeviceImpl;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
public class ImageTypeController {

    private ImageTypeSeviceImpl imageTypeSevice;

    private Optional<ImageType> imageTypeTemp;

    @GetMapping("/imageType")
    public String getIndex(ModelMap modelMap) {
        List<ImageType> imageTypes = imageTypeSevice.getAllImageTypes();
        List<ImageTypeDto> imageTypeDtos = new ArrayList<>();
        for (ImageType imageType : imageTypes) {
            imageTypeDtos.add(new ModelMapper().map(imageType, ImageTypeDto.class));
        }

        System.out.println(JsonUtils.gsonPretty(imageTypeDtos));
        modelMap.addAttribute("imageTypeDtos", imageTypeDtos);
        return "seting/imageType/index";
    }

    @GetMapping("/imageType/new")
    public String getNew(ModelMap modelMap) {
        modelMap.addAttribute("imageTypeForm", new ImageTypeDtoForm());
        return "seting/imageType/new";
    }

    @PostMapping("/imageType/new")
    public String postNew(@ModelAttribute("imageTypeForm")@Valid ImageTypeDtoForm imageTypeDtoForm, BindingResult result) {

        if (result.hasErrors()) {
            return "seting/imageType/new";
        }

        System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
        System.out.println(JsonUtils.gsonPretty(imageTypeDtoForm));
        System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");

        ImageType imageType = new ModelMapper().map(imageTypeDtoForm, ImageType.class);
        imageTypeSevice.saveImageType(imageType);

        return "redirect:/imageType";
    }

    @GetMapping("/imageType/edit/{imageTypeId}")
    public String getEdit(@PathVariable Long imageTypeId, ModelMap modelMap ) {
        imageTypeTemp = Optional.ofNullable(imageTypeSevice.getImageTypeById(imageTypeId));
        ImageTypeDtoForm imageTypeDtoForm = new ModelMapper().map(imageTypeTemp, ImageTypeDtoForm.class);

        modelMap.addAttribute("imageTypeForm", imageTypeDtoForm);
        return "seting/imageType/edit";
    }

    @PostMapping("/imageType/edit")
    public String postEdit(@ModelAttribute("imageTypeForm")@Valid ImageTypeDtoForm imageTypeDtoForm, BindingResult result) {
        ImageType imageType = new ImageType();
        if (result.hasErrors()) {
            return "seting/imageType/edit";
        }

        imageType = new ModelMapper().map(imageTypeDtoForm, ImageType.class);
        imageType.setId(imageTypeTemp.get().getId());
        imageType.setCreated_at(imageTypeTemp.get().getCreated_at());
        imageTypeSevice.saveImageType(imageType);
        return "redirect:/imageType";
    }

    @GetMapping("/imageType/delete/{imageTypeId}")
    public String getDelete(@PathVariable Long imageTypeId) {

        imageTypeSevice.deleteImageTypeById(imageTypeId);

        return "redirect:/imageType";
    }
}
