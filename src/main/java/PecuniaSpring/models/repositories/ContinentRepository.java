package PecuniaSpring.models.repositories;

import PecuniaSpring.models.Continent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContinentRepository extends JpaRepository<Continent, Long> {
    @Query(value = "SELECT con FROM Continent con WHERE con.continentEn = ?1")
    Continent getContinent(String continentEn);
}
