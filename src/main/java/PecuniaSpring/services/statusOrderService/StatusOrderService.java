package PecuniaSpring.services.statusOrderService;

import PecuniaSpring.models.StatusOrder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface StatusOrderService {
    List<StatusOrder> getAllStatusOrder();
    void saveStatusOrder(StatusOrder statusOrder);
    StatusOrder saveStatusOrderGet(StatusOrder statusOrder);
    StatusOrder getStatusOrderFindById(Long id);
    void deleteStatusOrderById(Long id);
}
