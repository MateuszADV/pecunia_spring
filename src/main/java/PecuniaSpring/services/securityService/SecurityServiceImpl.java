package PecuniaSpring.services.securityService;

import PecuniaSpring.models.Security;
import PecuniaSpring.models.repositories.SecurityRepository;
import PecuniaSpring.models.sqlClass.GetSecuritiesByStatus;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class SecurityServiceImpl implements SecurityService {

    private SecurityRepository securityRepository;

    @Override
    public List<Security> getAllSecurity() {
        return this.securityRepository.findAll();
    }

    @Override
    public List<Security> getAllSecurityOrderByID() {
        return this.securityRepository.getAllSecurityOrderById();
    }

    @Override
    public Security getSecurityById(Long id) {
        Optional<Security> security = securityRepository.findById(id);
        if (security.isPresent()) {
            return security.get();
        } else {
            throw new RuntimeException("Security Not Found From id :: " + id);
        }
    }

    @Override
    public Security saveSecurity(Security security) {
        return this.securityRepository.save(security);
    }

    @Override
    public void deleteSecurityById(Long id) {
        if (securityRepository.existsById(id)) {
            this.securityRepository.deleteById(id);
        } else {
            throw new RuntimeException("Record by Id - " + id + " not exist");
        }
    }

    @Override
    public List<Security> getSecurityByCurrencyId(Long currencyId) {
        List<Security> securities = securityRepository.getSecurityByCurrencyId(currencyId);
        return securities;
    }

    @Override
    public List<GetSecuritiesByStatus> getSecurityByStatus(String status) {
        List<Object[]> objects = securityRepository.getSecuritiesByStatus(status);
        List<GetSecuritiesByStatus> getSecuritiesByStatusList = new ArrayList<>();
        for (Object[] object : objects) {
            getSecuritiesByStatusList.add(new ModelMapper().map(object[0], GetSecuritiesByStatus.class));
        }
        return getSecuritiesByStatusList;
    }
}
