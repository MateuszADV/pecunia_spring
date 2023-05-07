package PecuniaSpring.models.repositories;

import PecuniaSpring.models.Coin;
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
}
