package PecuniaSpring.models.repsitories;

import PecuniaSpring.models.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CountryRepository extends JpaRepository<Country, Long> {
    @Query(value = "SELECT cou FROM Country cou ORDER BY cou.country_en ASC ")
    List<Country> countriesOrderByCountry_enAsc();
}
