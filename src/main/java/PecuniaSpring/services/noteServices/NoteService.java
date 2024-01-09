package PecuniaSpring.services.noteServices;

import PecuniaSpring.models.Note;
import PecuniaSpring.models.sqlClass.CountryByStatus;
import PecuniaSpring.models.sqlClass.CurrencyByStatus;
import PecuniaSpring.models.sqlClass.GetNotesByStatus;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface NoteService {
    Note getNoteById(Long id);
    List<Note> getNoteByCurrencyId(Long currencyId);
    List<Note> getNoteByCurrencyId(Long currencyId, String role);
    void deleteNoteById(Long id);

    //    *****************************************
    //    ******Query związane z Note *************
    //    *****************************************

    List<CountryByStatus> getCountryByStatus(String continent, String status, String role);

    List<CurrencyByStatus> getCurrencyByStatus(Long countryId, String status, String role);

    Page<Note> findNotePaginated(Integer pageNo, Integer pageSize, Long currencyId, String status, String role);

    List<GetNotesByStatus> getNoteByStatus(String status);
    List<GetNotesByStatus> getNoteByStatus(String status, String statusSell);
    List<GetNotesByStatus> getNoteByStatus(String status, Long countryId);

    List<CountryByStatus> getCountryByStatusNote(String status);

}
