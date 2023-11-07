package PecuniaSpring.services.securityService;

import PecuniaSpring.models.Security;
import PecuniaSpring.models.repositories.SecurityRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
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
}
