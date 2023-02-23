package PecuniaSpring.services.imageTypeService;

import PecuniaSpring.models.ImageType;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ImageTypeSevice {
    List<ImageType> getAllImageTypes();
    void saveImageType(ImageType imageType);
    ImageType getImageTypeById(Long id);
    void deleteImageTypeById(Long id);
}