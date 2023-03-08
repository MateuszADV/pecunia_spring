package PecuniaSpring.services.noteServices;

import PecuniaSpring.models.Note;
import PecuniaSpring.models.repositories.NoteRepository;
import PecuniaSpring.models.sqlClass.CountryByStatus;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    //    *****************************************
    //    ******Query związane z Note *************
    //    *****************************************


    @Override
    public List<CountryByStatus> getCountryByStatus(String continent, String status, String role) {
        List<Object[]> objects = new ArrayList<>();

        List<CountryByStatus> countryByStatusList = new ArrayList<>();


        if (role == "ADMIN") {
            objects = noteRepository.countryByStatus(status, continent);
            for (Object[] object : objects) {
                countryByStatusList.add(new ModelMapper().map(object[0], CountryByStatus.class));
            }
        } else {
            objects = noteRepository.countryByStatus(status, continent, true);
            for (Object[] object : objects) {
                countryByStatusList.add(new ModelMapper().map(object[0], CountryByStatus.class));
            }
        }
        return countryByStatusList;
    }
}
