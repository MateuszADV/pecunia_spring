package PecuniaSpring.services.boughtServices;

import PecuniaSpring.models.Bought;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BoughtServices {
    List<Bought>getAllBought();
    List<Bought>getAllOrderById();
    Bought getBoughtById(Long id);
    Bought saveBought(Bought bought);
    void deleteBoughtById(Long id);

    void updateBought(Bought bought);
}
