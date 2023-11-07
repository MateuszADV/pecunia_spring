package PecuniaSpring.models.repositories;

import PecuniaSpring.models.Security;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SecurityRepository extends JpaRepository<Security, Long> {

    @Query(value = "SELECT sec FROM Security sec ORDER BY sec.id")
    List<Security> getAllSecurityOrderById();
}
