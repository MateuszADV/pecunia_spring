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

    public List<Object[]> my_report_note_bought() {
        Query query = entityManager.createQuery("SELECT bou.name, COUNT(note.boughts) FROM Note note" +
                "  LEFT JOIN Bought bou" +
                "    ON bou.id = note.boughts" +
                " GROUP BY bou.name");
        List<Object[]> objects = query.getResultList();
        return objects;
    }

    public List<Object[]> my_report_notes_bought_country(String country) {
        Query query = entityManager.createQuery("SELECT bou.name, COUNT(note.boughts) FROM Note note" +
                "  LEFT JOIN Currency cur" +
                "    ON note.currencies = cur.id" +
                "  LEFT JOIN Country  cou" +
                "    ON cou.id = cur.countries" +
                "  LEFT JOIN Bought bou" +
                "    ON bou.id = note.boughts" +
                " WHERE cou.countryEn = '" + country + "'" +
                " GROUP BY bou.name");
        List<Object[]> objects = query.getResultList();
        return objects;
    }

    public List<Object[]> my_report_notes_add() {
        Query query = entityManager.createQuery("SELECT function('to_char', note.created_at, 'YYYY-MM'), COUNT(note.created_at) FROM Note note" +
                " GROUP BY function('to_char', note.created_at, 'YYYY-MM')  " +
                " ORDER BY function('to_char', note.created_at, 'YYYY-MM')");
        List<Object[]> objects = query.getResultList();
        return objects;
    }

    public List<Object[]> my_report_notes_buy() {
        Query query = entityManager.createQuery("SELECT function('to_char', note.dateBuy, 'YYYY-MM'), COUNT(note.dateBuy) FROM Note note" +
                " GROUP BY function('to_char', note.dateBuy, 'YYYY-MM')  " +
                " ORDER BY function('to_char', note.dateBuy, 'YYYY-MM')");
        List<Object[]> objects = query.getResultList();
        return objects;
    }

    public List<Object[]> my_report_coin_bought() {
        Query query = entityManager.createQuery("SELECT bou.name, COUNT(coin.boughts) FROM Coin coin" +
                "  LEFT JOIN Bought bou" +
                "    ON bou.id = coin.boughts" +
                " GROUP BY bou.name");
        List<Object[]> objects = query.getResultList();
        return objects;
    }

    public List<Object[]> my_report_coins_bought_country(String country) {
        Query query = entityManager.createQuery("SELECT bou.name, COUNT(coin.boughts) FROM Coin coin" +
                "  LEFT JOIN Currency cur" +
                "    ON coin.currencies = cur.id" +
                "  LEFT JOIN Country  cou" +
                "    ON cou.id = cur.countries" +
                "  LEFT JOIN Bought bou" +
                "    ON bou.id = coin.boughts" +
                " WHERE cou.countryEn = '" + country + "'"  +
                " GROUP BY bou.name");
        List<Object[]> objects = query.getResultList();
        return objects;
    }

    public List<Object[]> my_report_coins_add() {
        Query query = entityManager.createQuery("SELECT function('to_char', coin.created_at, 'YYYY-MM'), COUNT(coin.created_at) FROM Coin coin" +
                " GROUP BY function('to_char', coin.created_at, 'YYYY-MM')  " +
                " ORDER BY function('to_char', coin.created_at, 'YYYY-MM')");
        List<Object[]> objects = query.getResultList();
        return objects;
    }
}
