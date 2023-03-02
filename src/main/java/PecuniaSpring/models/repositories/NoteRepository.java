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


    @Query(value = "SELECT new map(con.continentEn AS continent, con.continentCode AS continentCode, cou.id AS countryId, cou.countryEn AS countryEn, cou.countryPl AS countryPl, COUNT(cou.countryEn) AS total) " +
                   "  FROM Note note" +
                   "  LEFT JOIN Status stat" +
                   "    ON stat.status = ?1" +
                   "  LEFT JOIN Currency cur" +
                   "    ON cur.id = note.currencies" +
                   "  LEFT JOIN Country cou" +
                   "    ON cou.id = cur.countries" +
                   "  LEFT JOIN Continent con" +
                   "    ON con.id = cou.continents" +
                   " WHERE stat.id = note.statuses AND con.continentEn = ?2" +
                   " GROUP BY cou.countryEn, cou.countryPl, cou.id, con.continentEn, con.continentCode" +
                   " ORDER BY cou.countryEn")
    List<Object[]> countryByStatus(String status, String continent);

}
