package PecuniaSpring.services.securityService;

import PecuniaSpring.models.Security;
import PecuniaSpring.models.sqlClass.CountryByStatus;
import PecuniaSpring.models.sqlClass.CurrencyByStatus;
import PecuniaSpring.models.sqlClass.GetSecuritiesByStatus;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SecurityService {

    List<Security> getAllSecurity();
    List<Security> getAllSecurityOrderByID();
    Security getSecurityById(Long id);
    Security saveSecurity(Security security);
    void deleteSecurityById(Long id);

    List<Security> getSecurityByCurrencyId(Long currencyId);

    List<GetSecuritiesByStatus> getSecurityByStatus(String status);

//    List<CountryByStatus> getCountryByStatus(String status);
    List<CountryByStatus> getCountryByStatus(String status, String role);
    List<CurrencyByStatus> getCurrencyByStatus(Long countryId, String status, String role);
    Page<Security> findSecurityPaginated(Integer pageNo, Integer pageSize, Long currencyId, String status, String role);
}
