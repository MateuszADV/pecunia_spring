package PecuniaSpring.services.noteServices;

import PecuniaSpring.models.Note;
import PecuniaSpring.models.repositories.NoteRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class NoteServiceImpl implements NoteService {

    private NoteRepository noteRepository;

    @Override
    public List<Note> getNoteByCurrencyId(Long currencyId) {
        List<Note> notes = noteRepository.getNoteByCurrencyId(currencyId);
        return notes;
    }
}
