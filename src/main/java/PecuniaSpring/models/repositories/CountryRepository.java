package PecuniaSpring.models.repositories;

import PecuniaSpring.models.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

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
}
