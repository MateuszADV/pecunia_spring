package PecuniaSpring.services.noteServices;

import PecuniaSpring.models.Note;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface NoteService {
    Note getNoteById(Long id);
    List<Note> getNoteByCurrencyId(Long currencyId);
    void deleteNoteById(Long id);
}
