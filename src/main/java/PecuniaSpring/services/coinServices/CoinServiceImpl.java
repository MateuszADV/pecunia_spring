package PecuniaSpring.services.coinServices;

import PecuniaSpring.models.Coin;
import PecuniaSpring.models.repositories.CoinRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CoinServiceImpl implements CoinService {

    private CoinRepository coinRepository;

    @Autowired
    public CoinServiceImpl(CoinRepository coinRepository) {
        this.coinRepository = coinRepository;
    }

    @Override
    public List<Coin> getAllCoin() {
        return coinRepository.findAll();
    }

    @Override
    public void saveCoin(Coin coin) {
        this.coinRepository.save(coin);
    }

    @Override
    public Coin getCoinById(Long id) {
        Optional<Coin> optional = coinRepository.findById(id);
        if (optional.isPresent()) {
            return optional.get();
        } else {
            throw new RuntimeException("Quality Not Found For Id :: " + id);
        }
    }

    @Override
    public void deleteCoinById(Long id) {
        this.coinRepository.deleteById(id);
    }

    @Override
    public List<Coin> getCoinByCurrencyId(Long currencyId) {
        List<Coin> coins = coinRepository.getCoinByCurrencyId(currencyId);
        return coins;
    }

    @Override
    public List<Coin> getCoinByCurrencyId(Long currencyId, String role) {
        List<Coin> coins = new ArrayList<>();
        if (role == "ADMIN") {
            coins = coinRepository.getCoinByCurrencyId(currencyId);
        } else {
            coins = coinRepository.getCoinByCurrencyId(currencyId, true);
        }
        return coins;
    }
}
