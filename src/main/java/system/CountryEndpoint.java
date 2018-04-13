package system;

import country.GetCountryRequest;
import country.GetCountryResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import repository.CountryRepository;


@Endpoint
@ComponentScan("repository")
public class CountryEndpoint {

    private static final String NAMESPACE_URI = "http://spring.io/guides/gs-producing-web-service";

    private CountryRepository countryRepository;

    @Autowired
    public CountryEndpoint(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }

    @PayloadRoot(localPart = "getCountryRequest", namespace = NAMESPACE_URI)
    @ResponsePayload
    public GetCountryResponse getCountry(@RequestPayload GetCountryRequest request) {

        GetCountryResponse response = new GetCountryResponse();
        response.setCountry(countryRepository.findCountry(request.getName()));

        return response;
    }
}