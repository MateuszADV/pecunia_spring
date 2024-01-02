package PecuniaSpring.controllers.viewControllers;

import PecuniaSpring.models.sqlClass.GetCoinsByStatus;
import PecuniaSpring.models.sqlClass.GetNotesByStatus;
import PecuniaSpring.services.coinServices.CoinServiceImpl;
import PecuniaSpring.services.noteServices.NoteServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@AllArgsConstructor
public class ItemController {

    private CoinServiceImpl coinService;
    private NoteServiceImpl noteService;

    @GetMapping("/itemForSell")
    public String getForSell(ModelMap modelMap) {
        List<GetCoinsByStatus> getCoinsByStatusList = coinService.getCoinsByStatus("FOR SELL");
        List<GetNotesByStatus> getNotesByStatusList = noteService.getNoteByStatus("FOR SELL");

        modelMap.addAttribute("forSellNotesList", getNotesByStatusList);
        modelMap.addAttribute("forSellCoinsList", getCoinsByStatusList);
        modelMap.addAttribute("title", "Item For Sell");
        return "item/index";
    }

    @GetMapping("/itemOnDisplay")
    public String getOnDisplay(ModelMap modelMap) {
        List<GetCoinsByStatus> getCoinsByStatusList = coinService.getCoinsByStatus("FOR SELL", "");
        List<GetNotesByStatus> getNotesByStatusList = noteService.getNoteByStatus("FOR SELL", "");

        modelMap.addAttribute("forSellNotesList", getNotesByStatusList);
        modelMap.addAttribute("forSellCoinsList", getCoinsByStatusList);
        modelMap.addAttribute("title", "Item On Display");

        return "item/index";
    }

    @GetMapping("/itemSold")
    public String getSold(ModelMap modelMap) {
        List<GetCoinsByStatus> getCoinsByStatusList = coinService.getCoinsByStatus("SOLD");
        List<GetNotesByStatus> getNotesByStatusList = noteService.getNoteByStatus("SOLD");

        modelMap.addAttribute("forSellNotesList", getNotesByStatusList);
        modelMap.addAttribute("forSellCoinsList", getCoinsByStatusList);
        modelMap.addAttribute("title", "Item Sold");

        return "item/index";
    }
}
