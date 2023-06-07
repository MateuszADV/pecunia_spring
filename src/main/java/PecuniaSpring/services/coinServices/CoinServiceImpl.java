package PecuniaSpring.services.coinServices;

import PecuniaSpring.models.Coin;
import PecuniaSpring.models.repositories.CoinRepository;
import PecuniaSpring.models.sqlClass.CountryByStatus;
import PecuniaSpring.models.sqlClass.CurrencyByStatus;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

    @Override
    public List<CountryByStatus> getCountryByStatus(String status, String role) {
        List<Object[]> objects = new ArrayList<>();
        List<CountryByStatus> countryByStatusList = new ArrayList<>();

        if (role == "ADMIN") {
            objects = coinRepository.countryByStatus(status);
            for (Object[] object : objects) {
                countryByStatusList.add(new ModelMapper().map(object[0], CountryByStatus.class));
            }
        } else {
            objects = coinRepository.countryByStatus(status, true);
            for (Object[] object : objects) {
                countryByStatusList.add(new ModelMapper().map(object[0], CountryByStatus.class));
            }
        }
        return countryByStatusList;
    }

    @Override
    public List<CurrencyByStatus> getCurrencyByStatus(Long countryId, String status, String role) {
        List<Object[]> objects = new ArrayList<>();
        List<CurrencyByStatus> currencyByStatusList = new ArrayList<>();

        if (role == "ADMIN") {
            objects = coinRepository.currencyByStatus(status, countryId);
            for (Object[] object : objects) {
                currencyByStatusList.add(new ModelMapper().map(object[0], CurrencyByStatus.class));
            }
        } else {
            objects = coinRepository.currencyByStatus(status, countryId, true);
            for (Object[] object : objects) {
                currencyByStatusList.add(new ModelMapper().map(object[0], CurrencyByStatus.class));
            }
        }
        return currencyByStatusList;
    }

    @Override
    public Page<Coin> findCoinPaginated(Integer pageNo, Integer pageSize, Long currencyId, String status, String role) {
        List<Coin> coins = new ArrayList<>();
        if (role == "ADMIN") {
            Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
            return this.coinRepository.coinPageable(currencyId, status, pageable);
        } else {
            Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
            return this.coinRepository.coinPageable(currencyId, status, true, pageable);
        }
    }
}
