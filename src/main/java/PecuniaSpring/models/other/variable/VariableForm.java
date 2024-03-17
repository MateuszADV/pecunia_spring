package PecuniaSpring.models.other.variable;

import PecuniaSpring.models.dto.active.ActiveDtoSelect;
import PecuniaSpring.models.dto.bought.BoughtDto;
import PecuniaSpring.models.dto.currency.CurrencyDto;
import PecuniaSpring.models.dto.imageType.ImageTypeDtoSelect;
import PecuniaSpring.models.dto.making.MakingDtoSelect;
import PecuniaSpring.models.dto.quality.QualityDtoSelect;
import PecuniaSpring.models.dto.status.StatusDtoSelect;
import org.springframework.stereotype.Component;
import org.springframework.ui.ModelMap;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@Component
public class VariableForm {
    public static void variableToSelect(ModelMap modelMap, List<CurrencyDto> currencyDtos, List<BoughtDto> boughtDtos, List<ActiveDtoSelect> activeDtoSelects, List<MakingDtoSelect> makingDtoSelects, List<QualityDtoSelect> qualityDtoSelects, List<StatusDtoSelect> statusDtoSelects, List<ImageTypeDtoSelect> imageTypeDtoSelects) {
        modelMap.addAttribute("currencies", currencyDtos);
        modelMap.addAttribute("boughts", boughtDtos);
        modelMap.addAttribute("actives", activeDtoSelects);
        modelMap.addAttribute("makings", makingDtoSelects);
        modelMap.addAttribute("qualities", qualityDtoSelects);
        modelMap.addAttribute("statuses", statusDtoSelects);
        modelMap.addAttribute("imageTypes", imageTypeDtoSelects);
        modelMap.addAttribute("standartDate", Date.valueOf(LocalDate.now()));
    }

}
