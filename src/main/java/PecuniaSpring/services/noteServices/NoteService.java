package PecuniaSpring.services.noteServices;

import PecuniaSpring.models.Note;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface NoteService {
    List<Note> getNoteByCurrencyId(Long currencyId);
}
