package PecuniaSpring.models.repositories;

import PecuniaSpring.models.Security;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SecurityRepository extends JpaRepository<Security, Long> {

    @Query(value = "SELECT sec FROM Security sec ORDER BY sec.id")
    List<Security> getAllSecurityOrderById();

    @Query(value = "SELECT sec FROM Security sec " +
            "WHERE sec.currencies.id = ?1")
    List<Security> getSecurityByCurrencyId(Long currencyId);

    @Query(value = "SELECT new map(sec.qualities AS qualities, sec.makings AS makings, sec.id AS securityId, cou.id AS countryId, cou.countryEn AS countryEn, cou.countryPl AS countryPl, cur.id AS currencyId, " +
            "cur.currencySeries AS currencySeries, cur.patterns AS patterns, bou.name AS bought, sec.denomination AS denomination, sec.nameCurrency AS nameCurrency, sec.itemDate AS itemDate, " +
            "sec.priceBuy AS priceBuy, sec.priceSell AS priceSell, sec.quantity AS quantity, sec.unitQuantity AS unitQuantity, " +
            "sec.width AS width, sec.height AS height, sec.visible AS visible, sec.description AS description, " +
            "sec.aversPath AS aversPath, sec.reversePath AS reversePath ) " +
            "  FROM Security sec" +
            "  LEFT JOIN Status stat" +
            "    ON stat.status = ?1" +
            "  LEFT JOIN Bought bou" +
            "    ON bou.id = sec.boughts" +
            "  LEFT JOIN Currency cur" +
            "    ON cur.id = sec.currencies" +
            "  LEFT JOIN Country cou" +
            "    ON cou.id = cur.countries" +
            " WHERE stat.id = sec.statuses" +
            " GROUP BY sec.qualities, sec.makings, sec.id, cou.id, cou.countryEn, cou.countryPl, cur.id, cur.currencySeries, cur.patterns, bou.name, sec.denomination, sec.nameCurrency, sec.itemDate, " +
            "          sec.priceBuy, sec.priceSell, sec.quantity, sec.unitQuantity, sec.width, sec.height, sec.visible, sec.description, sec.aversPath, sec.reversePath " +
            " ORDER BY cou.countryEn, sec.denomination")
    List<Object[]> getSecuritiesByStatus(String status);

    @Query(value = "SELECT new map(con.continentEn AS continent, con.continentCode AS continentCode, cou.id AS countryId, cou.countryEn AS countryEn, cou.countryPl AS countryPl, COUNT(cou.countryEn) AS total) " +
            "  FROM Security security" +
            "  LEFT JOIN Status stat" +
            "    ON stat.status = ?1" +
            "  LEFT JOIN Currency cur" +
            "    ON cur.id = security.currencies" +
            "  LEFT JOIN Country cou" +
            "    ON cou.id = cur.countries" +
            "  LEFT JOIN Continent con" +
            "    ON con.id = cou.continents" +
            " WHERE stat.id = security.statuses" +
            " GROUP BY cou.countryEn, cou.countryPl, cou.id, con.continentEn, con.continentCode" +
            " ORDER BY cou.countryEn")
    List<Object[]> countryByStatus(String status);

    @Query(value = "SELECT new map(con.continentEn AS continent, con.continentCode AS continentCode, cou.id AS countryId, cou.countryEn AS countryEn, cou.countryPl AS countryPl, COUNT(cou.countryEn) AS total) " +
            "  FROM Security security" +
            "  LEFT JOIN Status stat" +
            "    ON stat.status = ?1" +
            "  LEFT JOIN Currency cur" +
            "    ON cur.id = security.currencies" +
            "  LEFT JOIN Country cou" +
            "    ON cou.id = cur.countries" +
            "  LEFT JOIN Continent con" +
            "    ON con.id = cou.continents" +
            " WHERE stat.id = security.statuses AND security.visible = ?2" +
            " GROUP BY cou.countryEn, cou.countryPl, cou.id, con.continentEn, con.continentCode" +
            " ORDER BY cou.countryEn")
    List<Object[]> countryByStatus(String status, Boolean visible);

    @Query(value = "SELECT new map(cou.id AS countryId, cou.countryEn AS countryEn, cou.countryPl AS countryPl, cur.id AS currencyId, " +
            "cur.currencySeries AS currencySeries, COUNT(cur.currencySeries) AS total) " +
            "  FROM Security security" +
            "  LEFT JOIN Status stat" +
            "    ON stat.status = ?1" +
            "  LEFT JOIN Currency cur" +
            "    ON cur.id = security.currencies" +
            "  LEFT JOIN Country cou" +
            "    ON cou.id = cur.countries" +
            " WHERE stat.id = security.statuses AND cou.id = ?2" +
            " GROUP BY cou.countryEn, cou.countryPl, cou.id, cur.currencySeries, cur.id" +
            " ORDER BY cur.currencySeries")
    List<Object[]> currencyByStatus(String status, Long countryId);

    @Query(value = "SELECT new map(cou.id AS countryId, cou.countryEn AS countryEn, cou.countryPl AS countryPl, cur.id AS currencyId, " +
            "cur.currencySeries AS currencySeries, COUNT(cur.currencySeries) AS total) " +
            "  FROM Security security" +
            "  LEFT JOIN Status stat" +
            "    ON stat.status = ?1" +
            "  LEFT JOIN Currency cur" +
            "    ON cur.id = security.currencies" +
            "  LEFT JOIN Country cou" +
            "    ON cou.id = cur.countries" +
            " WHERE stat.id = security.statuses AND cou.id = ?2 AND security.visible = ?3" +
            " GROUP BY cou.countryEn, cou.countryPl, cou.id, cur.currencySeries, cur.id" +
            " ORDER BY cur.currencySeries")
    List<Object[]> currencyByStatus(String status, Long countryId, Boolean visible);

    @Query(value = "SELECT security FROM Security security " +
            "  LEFT JOIN Status stat " +
            "    ON stat.status = ?2 " +
            "WHERE security.currencies.id = ?1 AND stat.id = security.statuses " +
            "ORDER BY security.denomination")
    Page<Security> securityPageable(Long currencyId, String status, final Pageable pageable);

    @Query(value = "SELECT security FROM Security security " +
            "  LEFT JOIN Status stat " +
            "    ON stat.status = ?2" +
            "WHERE security.currencies.id = ?1 AND stat.id = security.statuses  AND security.visible = ?3 " +
            "ORDER BY security.denomination")
    Page<Security> securityPageable(Long currencyId, String status, Boolean visible, final Pageable pageable);
}
