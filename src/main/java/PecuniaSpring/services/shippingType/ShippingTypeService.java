package PecuniaSpring.services.shippingType;

import PecuniaSpring.models.ShippingType;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ShippingTypeService {
    List<ShippingType> getAllShippingType();
    void saveShippingType(ShippingType shippingType);
    ShippingType saveShippingTypeGet(ShippingType shippingType);
    ShippingType getShippingTypeFindById(Long id);
    void deleteShippingTypeById(Long id);
}
