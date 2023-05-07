package PecuniaSpring.models.repositories;

import PecuniaSpring.models.Currency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CurrencyRepository extends JpaRepository<Currency, Long> {
    @Query(value = "SELECT cur FROM Currency cur " +
                   "WHERE cur.countries.id = ?1 " +
                   "AND cur.pattern = ?2 " +
                   "ORDER BY cur.currencySeries ASC ")
    List<Currency> getCurrencyByCountryByPattern(Long countryId, String pattern);
}
