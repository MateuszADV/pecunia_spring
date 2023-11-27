package PecuniaSpring.services.shippingType;

import PecuniaSpring.models.ShippingType;
import PecuniaSpring.models.repositories.ShippingTypeRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ShippingTypeServiceImpl implements ShippingTypeService {

    private ShippingTypeRepository shippingTypeRepository;

    @Override
    public List<ShippingType> getAllShippingType() {
        return this.shippingTypeRepository.findAll();
    }

    @Override
    public void saveShipping(ShippingType shippingType) {

    }

    @Override
    public ShippingType saveShippingType(ShippingType shippingType) {
        return null;
    }

    @Override
    public ShippingType getShippingTypeById(Long id) {
        return null;
    }

    @Override
    public void deleteShippingTypeById(Long id) {

    }
}
