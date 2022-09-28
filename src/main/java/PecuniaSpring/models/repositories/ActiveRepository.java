package PecuniaSpring.models.repositories;

import PecuniaSpring.models.Active;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ActiveRepository extends JpaRepository<Active, Long> {
}
