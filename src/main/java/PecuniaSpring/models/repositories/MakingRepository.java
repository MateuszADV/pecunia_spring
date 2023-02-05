package PecuniaSpring.models.repositories;

import PecuniaSpring.models.Making;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MakingRepository extends JpaRepository<Making, Long> {
}
