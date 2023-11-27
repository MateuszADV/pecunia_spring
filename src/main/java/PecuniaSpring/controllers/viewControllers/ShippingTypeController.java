package PecuniaSpring.controllers.viewControllers;

import PecuniaSpring.models.ShippingType;
import PecuniaSpring.models.dto.shippingType.ShippingTypeDto;
import PecuniaSpring.services.shippingType.ShippingTypeServiceImpl;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@AllArgsConstructor
public class ShippingTypeController {

    private ShippingTypeServiceImpl shippingTypeService;

    @GetMapping("/shipping")
    public String getIndex(ModelMap modelMap) {
        List<ShippingType> shippingTypes = shippingTypeService.getAllShippingType();
        List<ShippingTypeDto> shippingTypeDtos = new ArrayList<>();
        for (ShippingType shippingType : shippingTypes) {
            shippingTypeDtos.add(new ModelMapper().map(shippingType, ShippingTypeDto.class));
        }
        modelMap.addAttribute("shippingTypeDtos", shippingTypeDtos);
        return "seting/shippingType/index";
    }
}
