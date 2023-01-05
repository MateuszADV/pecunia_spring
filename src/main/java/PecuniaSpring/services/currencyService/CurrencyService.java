package PecuniaSpring.services.currencyService;

import PecuniaSpring.models.Currency;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CurrencyService {
    List<Currency> getAllCurrencis();
    void saveCurrency(Currency currency);
    Currency getCurrencyById(Long id);
    void deleteCurrencyById(Long id);
    List<Currency> getCurrencyByCountryByPattern(Long countryId, String pattern);
}
