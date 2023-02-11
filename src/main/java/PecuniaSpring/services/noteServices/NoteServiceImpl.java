package PecuniaSpring.services.noteServices;

import PecuniaSpring.models.Note;
import PecuniaSpring.models.repositories.NoteRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class NoteServiceImpl implements NoteService {

    private NoteRepository noteRepository;

    @Override
    public Note getNoteById(Long id) {
        Optional<Note> optional = noteRepository.findById(id);
        Note note = new Note();
        if (optional.isPresent()) {
            note = optional.get();
        }
        else {
            throw new RuntimeException("Note Not Found For Id :: "+ id);
        }
        return note;
    }

    @Override
    public List<Note> getNoteByCurrencyId(Long currencyId) {
        List<Note> notes = noteRepository.getNoteByCurrencyId(currencyId);
        return notes;
    }

    @Override
    public void deleteNoteById(Long id) {
        this.noteRepository.deleteById(id);
    }
}
