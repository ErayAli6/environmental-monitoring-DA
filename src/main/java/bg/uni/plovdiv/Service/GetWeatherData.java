package bg.uni.plovdiv.Service;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class GetWeatherData {

    @Value("${open-weather-map.api.key}")
    private String apiKey;

    private final GetCoordinatesByZipCode getCoordinatesByZipCode;

    private final RestTemplate restTemplate;

    public String execute(String zipCode, String countryCode) {
        JsonNode coordinate = getCoordinatesByZipCode.execute(zipCode, countryCode);
        String lon = coordinate.get("lon").asText();
        String lat = coordinate.get("lat").asText();
        String url = "https://api.openweathermap.org/data/3.0/onecall?lat=" + lat + "&lon=" + lon + "&appid=" + apiKey;
        return restTemplate.exchange(url, HttpMethod.GET, createRequest(), String.class).getBody();
    }

    private HttpEntity<String> createRequest() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new HttpEntity<>(headers);
    }
}
