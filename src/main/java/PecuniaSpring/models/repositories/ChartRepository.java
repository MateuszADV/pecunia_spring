package PecuniaSpring.models.repositories;

import PecuniaSpring.models.Country;
import PecuniaSpring.models.dto.country.CountryDto;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Repository;
import utils.JsonUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ChartRepository {
    @PersistenceContext
    private EntityManager entityManager;

    public List<Object[]> my_report_continents_test() {
        Query query = entityManager.createQuery("SELECT con.continentEn, COUNT(cou.continent) FROM Country cou " +
                "LEFT JOIN Continent con " +
                "ON cou.continents = con.id " +
                "GROUP BY con.continentEn");
        List<Object[]> objects = query.getResultList();
        return objects;
    }

    public List<Object[]> my_report_note_currency_country(String country) {
        Query query = entityManager.createQuery("SELECT cur.currencySeries, COUNT(note.currencies) FROM Note note" +
                                                   "  LEFT JOIN Currency cur" +
                                                   "    ON note.currencies = cur.id" +
                                                   "  LEFT JOIN Country  cou" +
                                                   "    ON cou.countryEn = '" + country + "'" +
                                                   " WHERE cou.id = cur.countries" +
                                                   " GROUP BY cur.currencySeries");
        List<Object[]> objects = query.getResultList();
        return objects;
    }

    public List<Object[]> my_report_object_status() {
        Query query = entityManager.createQuery("SELECT stat.status, COUNT(note.statuses) FROM Note note" +
                                                   "  LEFT JOIN Status stat" +
                                                   "    ON stat.id = note.statuses" +
                                                   " GROUP BY stat.status, note.statuses");
        List<Object[]> objects = query.getResultList();
        return objects;
    }

    public List<Object[]> my_report_coins_composition() {
        Query query = entityManager.createQuery("SELECT coin.composition, COUNT(coin.composition) FROM Coin coin" +
                                                    " GROUP BY coin.composition" +
                                                    " ORDER BY coin.composition DESC ");
        List<Object[]> objects = query.getResultList();
        return objects;
    }

    public List<Object[]> my_report_notes_bought() {
        Query query = entityManager.createQuery("SELECT bou.name, COUNT(note.boughts) FROM Note note" +
                "  LEFT JOIN Bought bou" +
                "    ON bou.id = note.boughts" +
                " GROUP BY bou.name, note.boughts");
        List<Object[]> objects = query.getResultList();
        return objects;
    }
}
