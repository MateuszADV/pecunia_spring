package PecuniaSpring.models.repositories;

import PecuniaSpring.models.Country;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Repository
public interface CountryRepository extends JpaRepository<Country, Long> {
    @Query(value = "SELECT cou FROM Country cou ORDER BY cou.countryEn ASC ")
    List<Country> countriesOrderByCountryEnAsc();

    @Query(value = "SELECT cou FROM Country cou WHERE cou.continent = ?1")
    List<Country> countries(String continentEn);

    @Query(value = "SELECT cou FROM Country cou " +
            "WHERE LOWER(cou.countryEn) LIKE LOWER(CONCAT('%', ?1, '%'))" +
            "OR    LOWER(cou.countryPl) LIKE LOWER(CONCAT('%', ?1, '%'))")
    List<Country> searchCountry(String keyWord);

    Country findByCountryEn(String countryEn);


//    *****************************************
//    ******Query związane z countries*********
//    *****************************************

    @Query(value = "SELECT new map(cou.continent AS continent, COUNT(cou.continent) AS total) FROM Country cou " +
                   "GROUP BY cou.continent")
    List<Object[]> countryByContinent();

    @Query(value = "SELECT new map(con.id AS continentId, con.continentPl AS continentPl, con.continentEn AS continentEn, " +
            "con.continentCode AS code, COUNT(cou.continent) AS total) FROM Country cou " +
            "LEFT JOIN Continent con " +
            "ON cou.continents = con.id " +
            "GROUP BY con.id, con.continentPl, continentEn, con.continentCode")
    List<Object[]> countCountry();

    //    *****************************************
    //    ******** Query Dane do Wykresów *********
    //    *****************************************

    @Query(value = "SELECT con.continentEn, COUNT(cou.continent) FROM Country cou " +
            "LEFT JOIN Continent con " +
            "ON cou.continents = con.id " +
            "GROUP BY con.continentEn")
    List<Object[]> reportCountCountry();

}
