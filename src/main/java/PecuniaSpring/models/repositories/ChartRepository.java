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

    public void testOne() {
        Query query = entityManager.createQuery("Select cou FROM Country cou ");
        List<Country> countries = query.getResultList();

        List<CountryDto> countryDtos = new ArrayList<>();
        for (Country country : countries) {
            countryDtos.add(new ModelMapper().map(country, CountryDto.class));
        }
        System.out.println("000000000START0000000000000000");
        System.out.println("NAPIS TESTOWY");
        System.out.println("000000000STOP0000000000000000");
    }

    public List<Object[]> my_report_continents_test() {
        Query query = entityManager.createQuery("SELECT con.continentEn, COUNT(cou.continent) FROM Country cou " +
                "LEFT JOIN Continent con " +
                "ON cou.continents = con.id " +
                "GROUP BY con.continentEn");

        List<Object[]> objects = query.getResultList();

        System.out.println("99999999999999999999999999999999999999999999");
        System.out.println("my_report_continents_test");
        System.out.println("99999999999999999999999999999999999999999999");
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
//        System.out.println("88888888888888888888888888888888888888");
//        System.out.println(query.getHints());
//        System.out.println(JsonUtils.gsonPretty(query.getHints()));
//        System.out.println("88888888888888888888888888888888888888");

        List<Object[]> objects = query.getResultList();

//        System.out.println(JsonUtils.gsonPretty(objects));
        return objects;
    }
}
