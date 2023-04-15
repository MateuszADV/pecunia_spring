package PecuniaSpring.services.apiService;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import org.springframework.stereotype.Service;

@Service
public class ApiServiceImpl implements ApiService {
    @Override
    public ClientResponse clientResponse(String url) {
        Client client = Client.create();
        WebResource webResource = client.resource(url);
        ClientResponse clientResponse = webResource.accept("application/json")
                .get(ClientResponse.class);

        if (clientResponse.getStatus() == 200) {
            client.destroy();
            return clientResponse;
        }
        else{
            throw new RuntimeException("Błąd pobrania... " + clientResponse.getStatusInfo() + " - " + clientResponse.getStatus());
        }
    }
}
