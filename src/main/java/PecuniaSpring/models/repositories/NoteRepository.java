package PecuniaSpring.models.repositories;

import PecuniaSpring.models.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoteRepository extends JpaRepository<Note, Long> {

    @Query(value = "SELECT note FROM Note note " +
                   "WHERE note.currencies.id = ?1")
    List<Note> getNoteByCurrencyId(Long currencyId);
}
