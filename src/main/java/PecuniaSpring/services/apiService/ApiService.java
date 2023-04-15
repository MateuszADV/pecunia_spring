package PecuniaSpring.services.apiService;

import com.sun.jersey.api.client.ClientResponse;
import org.springframework.stereotype.Service;


@Service
public interface ApiService {

    ClientResponse clientResponse(String url);
}
