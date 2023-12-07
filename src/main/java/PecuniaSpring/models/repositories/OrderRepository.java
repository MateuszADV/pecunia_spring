package PecuniaSpring.models.repositories;

import PecuniaSpring.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query(value = "SELECT ord FROM Order ord " +
                   " WHERE ord.customers.uniqueId = ?1 " +
                   " ORDER By ord.id")
    List<Order>getOrderbyCustomerUUID(String customerUUID);

    @Query(value = "SELECT * FROM orders  " +
                   " ORDER BY id DESC Limit 1", nativeQuery = true)
    Order getLastOrder();
}
