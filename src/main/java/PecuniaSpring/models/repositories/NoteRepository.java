package PecuniaSpring.models.repositories;

import PecuniaSpring.models.Currency;
import PecuniaSpring.models.Note;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoteRepository extends JpaRepository<Note, Long> {

    @Query(value = "SELECT note FROM Note note " +
                   "WHERE note.currencies.id = ?1")
    List<Note> getNoteByCurrencyId(Long currencyId);

    @Query(value = "SELECT note FROM Note note " +
            "WHERE note.currencies.id = ?1 AND note.visible = ?2")
    List<Note> getNoteByCurrencyId(Long currencyId, Boolean visible);


    /**
     * @param status
     * @param continent
     * @return
     */
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
            " WHERE stat.id = note.statuses AND con.continentEn = ?2 AND note.visible = ?3" +
            " GROUP BY cou.countryEn, cou.countryPl, cou.id, con.continentEn, con.continentCode" +
            " ORDER BY cou.countryEn")
    List<Object[]> countryByStatus(String status, String continent, Boolean visible);

    @Query(value = "SELECT new map(cou.id AS countryId, cou.countryEn AS countryEn, cou.countryPl AS countryPl, cur.id AS currencyId, " +
            "cur.currencySeries AS currencySeries, COUNT(cur.currencySeries) AS total) " +
            "  FROM Note note" +
            "  LEFT JOIN Status stat" +
            "    ON stat.status = ?1" +
            "  LEFT JOIN Currency cur" +
            "    ON cur.id = note.currencies" +
            "  LEFT JOIN Country cou" +
            "    ON cou.id = cur.countries" +
            " WHERE stat.id = note.statuses AND cou.id = ?2" +
            " GROUP BY cou.countryEn, cou.countryPl, cou.id, cur.currencySeries, cur.id" +
            " ORDER BY cur.currencySeries")
    List<Object[]> currencyByStatus(String status, Long countryId);

    @Query(value = "SELECT new map(cou.id AS countryId, cou.countryEn AS countryEn, cou.countryPl AS countryPl, cur.id AS currencyId, " +
            "cur.currencySeries AS currencySeries, COUNT(cur.currencySeries) AS total) " +
            "  FROM Note note" +
            "  LEFT JOIN Status stat" +
            "    ON stat.status = ?1" +
            "  LEFT JOIN Currency cur" +
            "    ON cur.id = note.currencies" +
            "  LEFT JOIN Country cou" +
            "    ON cou.id = cur.countries" +
            " WHERE stat.id = note.statuses AND cou.id = ?2 AND note.visible = ?3" +
            " GROUP BY cou.countryEn, cou.countryPl, cou.id, cur.currencySeries, cur.id" +
            " ORDER BY cur.currencySeries")
    List<Object[]> currencyByStatus(String status, Long countryId, Boolean visible);

    @Query(value = "SELECT new map(cou.id AS countryId, cou.countryEn AS countryEn, cou.countryPl AS countryPl, cur.id AS currencyId, " +
            "cur.currencySeries AS currencySeries, bou.name AS bought, note.denomination AS denomination, note.nameCurrency AS nameCurrency, note.itemDate AS itemDate, " +
            "note.priceBuy AS priceBuy, note.quantity AS quantity, note.description AS descryption, " +
            "note.aversPath AS aversPath, note.reversePath AS reversePath ) " +
            "  FROM Note note" +
            "  LEFT JOIN Status stat" +
            "    ON stat.status = ?1" +
            "  LEFT JOIN Bought bou" +
            "    ON bou.id = note.boughts" +
            "  LEFT JOIN Currency cur" +
            "    ON cur.id = note.currencies" +
            "  LEFT JOIN Country cou" +
            "    ON cou.id = cur.countries" +
            " WHERE stat.id = note.statuses AND bou.name = 'LOC'" +
            " GROUP BY cou.id, cou.countryEn, cou.countryPl, cur.id, cur.currencySeries, bou.name, note.denomination, note.nameCurrency, note.itemDate, " +
            "          note.priceBuy, note.quantity, note.description, note.aversPath, note.reversePath " +
            " ORDER BY cou.countryEn, note.denomination")
    List<Object[]> getNotesByStatus(String status);

    @Query(value = "SELECT note FROM Note note " +
                   "WHERE note.currencies.id = ?1 " +
                   "ORDER BY note.denomination")
    Page<Note> notePageable(Long currencyId, final Pageable pageable);

    @Query(value = "SELECT note FROM Note note " +
            "WHERE note.currencies.id = ?1 AND note.visible = ?2 " +
            "ORDER BY note.denomination")
    Page<Note> notePageable(Long currencyId, Boolean visible, final Pageable pageable);

}
