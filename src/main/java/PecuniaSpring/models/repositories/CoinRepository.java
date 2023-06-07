package PecuniaSpring.models.repositories;

import PecuniaSpring.models.Coin;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CoinRepository extends JpaRepository<Coin, Long> {

    @Query(value = "SELECT coin FROM Coin coin " +
                   " WHERE coin.currencies.id = ?1")
    List<Coin> getCoinByCurrencyId(Long currencyId);

    @Query(value = "SELECT coin FROM Coin coin " +
                   " WHERE coin.currencies.id = ?1 AND coin.visible = ?2")
    List<Coin> getCoinByCurrencyId(Long currencyId, Boolean visible);

    @Query(value = "SELECT new map(con.continentEn AS continent, con.continentCode AS continentCode, cou.id AS countryId, cou.countryEn AS countryEn, cou.countryPl AS countryPl, COUNT(cou.countryEn) AS total) " +
            "  FROM Coin coin" +
            "  LEFT JOIN Status stat" +
            "    ON stat.status = ?1" +
            "  LEFT JOIN Currency cur" +
            "    ON cur.id = coin.currencies" +
            "  LEFT JOIN Country cou" +
            "    ON cou.id = cur.countries" +
            "  LEFT JOIN Continent con" +
            "    ON con.id = cou.continents" +
            " WHERE stat.id = coin.statuses" +
            " GROUP BY cou.countryEn, cou.countryPl, cou.id, con.continentEn, con.continentCode" +
            " ORDER BY cou.countryEn")
    List<Object[]> countryByStatus(String status);

    @Query(value = "SELECT new map(con.continentEn AS continent, con.continentCode AS continentCode, cou.id AS countryId, cou.countryEn AS countryEn, cou.countryPl AS countryPl, COUNT(cou.countryEn) AS total) " +
            "  FROM Coin coin" +
            "  LEFT JOIN Status stat" +
            "    ON stat.status = ?1" +
            "  LEFT JOIN Currency cur" +
            "    ON cur.id = coin.currencies" +
            "  LEFT JOIN Country cou" +
            "    ON cou.id = cur.countries" +
            "  LEFT JOIN Continent con" +
            "    ON con.id = cou.continents" +
            " WHERE stat.id = coin.statuses AND coin.visible = ?2" +
            " GROUP BY cou.countryEn, cou.countryPl, cou.id, con.continentEn, con.continentCode" +
            " ORDER BY cou.countryEn")
    List<Object[]> countryByStatus(String status, Boolean visible);

    @Query(value = "SELECT new map(cou.id AS countryId, cou.countryEn AS countryEn, cou.countryPl AS countryPl, cur.id AS currencyId, " +
            "cur.currencySeries AS currencySeries, COUNT(cur.currencySeries) AS total) " +
            "  FROM Coin coin" +
            "  LEFT JOIN Status stat" +
            "    ON stat.status = ?1" +
            "  LEFT JOIN Currency cur" +
            "    ON cur.id = coin.currencies" +
            "  LEFT JOIN Country cou" +
            "    ON cou.id = cur.countries" +
            " WHERE stat.id = coin.statuses AND cou.id = ?2" +
            " GROUP BY cou.countryEn, cou.countryPl, cou.id, cur.currencySeries, cur.id" +
            " ORDER BY cur.currencySeries")
    List<Object[]> currencyByStatus(String status, Long countryId);

    @Query(value = "SELECT new map(cou.id AS countryId, cou.countryEn AS countryEn, cou.countryPl AS countryPl, cur.id AS currencyId, " +
            "cur.currencySeries AS currencySeries, COUNT(cur.currencySeries) AS total) " +
            "  FROM Coin coin" +
            "  LEFT JOIN Status stat" +
            "    ON stat.status = ?1" +
            "  LEFT JOIN Currency cur" +
            "    ON cur.id = coin.currencies" +
            "  LEFT JOIN Country cou" +
            "    ON cou.id = cur.countries" +
            " WHERE stat.id = coin.statuses AND cou.id = ?2 AND coin.visible = ?3" +
            " GROUP BY cou.countryEn, cou.countryPl, cou.id, cur.currencySeries, cur.id" +
            " ORDER BY cur.currencySeries")
    List<Object[]> currencyByStatus(String status, Long countryId, Boolean visible);

    @Query(value = "SELECT coin FROM Coin coin " +
            "  LEFT JOIN Status stat " +
            "    ON stat.status = ?2 " +
            "WHERE coin.currencies.id = ?1 AND stat.id = coin.statuses " +
            "ORDER BY coin.denomination")
    Page<Coin> coinPageable(Long currencyId, String status, final Pageable pageable);

    @Query(value = "SELECT coin FROM Coin coin " +
            "  LEFT JOIN Status stat " +
            "    ON stat.status = ?2" +
            "WHERE coin.currencies.id = ?1 AND stat.id = coin.statuses  AND coin.visible = ?3 " +
            "ORDER BY coin.denomination")
    Page<Coin> coinPageable(Long currencyId, String status, Boolean visible, final Pageable pageable);
}
