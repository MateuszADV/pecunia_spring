package PecuniaSpring.services.shippingType;

import PecuniaSpring.models.Pattern;
import PecuniaSpring.models.ShippingType;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ShippingTypeService {
    List<ShippingType> getAllShippingType();
    void saveShipping(ShippingType shippingType);
    ShippingType saveShippingType(ShippingType shippingType);
    ShippingType getShippingTypeById(Long id);
    void deleteShippingTypeById(Long id);
}
