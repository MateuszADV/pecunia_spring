package PecuniaSpring.services.statusService;

import PecuniaSpring.models.Status;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface StatusService {
    List<Status> getAllStatuses();
    void saveStatus(Status status);
    Status getStatusById(Long id);
    void deleteStatusById(Long id);
}
