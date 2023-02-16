package PecuniaSpring.services.statusService;

import PecuniaSpring.models.Status;
import PecuniaSpring.models.repositories.StatusRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class StatusServiceImpl implements StatusService {

    private StatusRepository statusRepository;

    @Override
    public List<Status> getAllStatuses() {
        return this.statusRepository.findAll();
    }

    @Override
    public void saveStatus(Status status) {
        this.statusRepository.save(status);
    }

    @Override
    public Status getStatusById(Long id) {
        Optional<Status> optional = statusRepository.findById(id);
        Status status = new Status();
        if (optional.isPresent()) {
            status = optional.get();
        }else {
            throw new RuntimeException("Status Not Found For Id :: " + id);
        }
        return status;
    }

    @Override
    public void deleteStatusById(Long id) {
        this.statusRepository.deleteById(id);
    }
}
