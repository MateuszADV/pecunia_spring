package PecuniaSpring.services.securityService;

import PecuniaSpring.models.Security;
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
}
