package PecuniaSpring.models.repositories;

import PecuniaSpring.models.Active;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ActiveRepository extends JpaRepository<Active, Long> {
//    @Query(value = "SELECT EXISTS (SELECT act.active_cod FROM Active act WHERE act.active_cod = 104)::BOOLEAN ")
    Boolean existsByActiveCod(Integer active_code);
//    @Query(value = "SELECT act FROM Active act WHERE act.activeCod = ?1")
    Active findByActiveCod(Integer activeCod);
}
