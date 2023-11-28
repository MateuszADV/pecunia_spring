package PecuniaSpring.models.repositories;

import PecuniaSpring.models.ShippingType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShippingTypeRepository extends JpaRepository<ShippingType, Long> {
}
