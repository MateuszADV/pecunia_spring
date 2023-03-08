package PecuniaSpring.services.noteServices;

import PecuniaSpring.models.Note;
import PecuniaSpring.models.sqlClass.CountryByStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface NoteService {
    Note getNoteById(Long id);
    List<Note> getNoteByCurrencyId(Long currencyId);
    void deleteNoteById(Long id);

    //    *****************************************
    //    ******Query związane z Note *************
    //    *****************************************

    List<CountryByStatus> getCountryByStatus(String continent, String status, String role);
}
