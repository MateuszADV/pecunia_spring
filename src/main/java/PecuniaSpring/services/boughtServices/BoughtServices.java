package PecuniaSpring.services.boughtServices;

import PecuniaSpring.models.Bought;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BoughtServices {
    List<Bought>getAllBought();
    List<Bought>getAllOrderById();
    Bought saveBought(Bought bought);
}
