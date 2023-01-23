package PecuniaSpring.services.boughtServices;

import PecuniaSpring.models.Bought;
import PecuniaSpring.models.repositories.BoughtRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class BoughtServicesImpl implements BoughtServices {

    private BoughtRepository boughtRepository;

    @Override
    public List<Bought> getAllBought() {
        return boughtRepository.findAll();
    }

    @Override
    public List<Bought> getAllOrderById() {
        return boughtRepository.getAllOrOrderById();
    }

    @Override
    public Bought saveBought(Bought bought) {
        return this.boughtRepository.save(bought);
    }
}
