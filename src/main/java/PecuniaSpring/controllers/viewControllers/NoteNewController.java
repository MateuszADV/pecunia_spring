package PecuniaSpring.controllers.viewControllers;

import PecuniaSpring.models.repositories.NoteRepository;
import PecuniaSpring.models.sqlClass.GetNotesByStatus;
import lombok.AllArgsConstructor;
import org.apache.catalina.LifecycleState;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import utils.JsonUtils;

import java.util.ArrayList;
import java.util.List;

@Controller
@AllArgsConstructor
public class NoteNewController {

    private NoteRepository noteRepository;

    @GetMapping("/note/newNotes")
    public String getIndex(ModelMap modelMap) {
        List<Object[]> objects = noteRepository.getNotesByStatus("NEW");
        List<GetNotesByStatus> getNotesByStatuses = new ArrayList<>();
        for (Object[] object : objects) {
            getNotesByStatuses.add(new ModelMapper().map(object[0],GetNotesByStatus.class));
        }
        System.out.println("PPPPPPPPPPPPPPPPPPPPPPPPPPP");
        System.out.println(objects.size());
        System.out.println(JsonUtils.gsonPretty(getNotesByStatuses));
        System.out.println("PPPPPPPPPPPPPPPPPPPPPPPPPPP");

        return "note/newNote/index";
    }

    @GetMapping("/note/newNotes/loc")
    public String getNewNotesLoc(ModelMap modelMap) {
        List<Object[]> objects = noteRepository.getNotesByStatus("NEW");
        List<GetNotesByStatus> getNotesByStatusList = new ArrayList<>();
        for (Object[] object : objects) {
            getNotesByStatusList.add(new ModelMapper().map(object[0],GetNotesByStatus.class));
        }

        modelMap.addAttribute("newNotesList", getNotesByStatusList);


        return "note/newNote/loc";
    }

}
