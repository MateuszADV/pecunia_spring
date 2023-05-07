package PecuniaSpring.services.coinServices;

import PecuniaSpring.models.Coin;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CoinService {
    List<Coin> getAllCoin();
    void saveCoin(Coin coin);
    Coin getCoinById(Long id);
    void deleteCoinById(Long id);

    List<Coin> getCoinByCurrencyId(Long currencyId);
    List<Coin> getCoinByCurrencyId(Long currencyId, String role);
}
