package PecuniaSpring.services.imageTypeService;

import PecuniaSpring.models.ImageType;
import PecuniaSpring.models.repositories.ImageTypeRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ImageTypeSeviceImpl implements ImageTypeSevice {

    private ImageTypeRepository imageTypeRepository;

    @Override
    public List<ImageType> getAllImageTypes() {
        return this.imageTypeRepository.findAll();
    }

    @Override
    public void saveImageType(ImageType imageType) {
        this.imageTypeRepository.save(imageType);
    }

    @Override
    public ImageType getImageTypeById(Long id) {
        Optional<ImageType> optional = imageTypeRepository.findById(id);
        ImageType imageType = new ImageType();
        if (optional.isPresent()){
            imageType = optional.get();

        }else {
            throw new RuntimeException("Image Type Not Found For Id :: " + id);
        }
        return imageType;
    }

    @Override
    public void deleteImageTypeById(Long id) {
        this.imageTypeRepository.deleteById(id);
    }
}
