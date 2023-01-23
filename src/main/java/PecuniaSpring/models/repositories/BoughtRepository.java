package PecuniaSpring.models.repositories;

import PecuniaSpring.models.Bought;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BoughtRepository extends JpaRepository<Bought, Long> {

    @Query(value = "SELECT bou FROM Bought bou ORDER BY bou.id")
    List<Bought> getAllOrOrderById();
}
