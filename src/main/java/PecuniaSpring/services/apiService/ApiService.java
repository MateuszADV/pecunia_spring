package PecuniaSpring.services.apiService;

import PecuniaSpring.models.other.GetApiMetal;
import PecuniaSpring.models.other.GetRateCurrencyTableA;
import com.sun.jersey.api.client.ClientResponse;
import org.springframework.stereotype.Service;


@Service
public interface ApiService {

    ClientResponse clientResponse(String url);
    GetApiMetal getApiMetal(String url);

    GetRateCurrencyTableA getRateCurrencyTableA(String url, String[] codes);
}
