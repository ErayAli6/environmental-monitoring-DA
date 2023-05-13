package bg.uni.plovdiv.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
@Slf4j
public class GetCoordinatesByZipCode {

    @Value("${open-weather-map.api.key}")
    private String apiKey;

    private final RestTemplate restTemplate;

    private final ObjectMapper objectMapper;

    public JsonNode execute(String zipCode, String countryCode) {
        String url = "http://api.openweathermap.org/geo/1.0/zip?zip=" + zipCode + "," + countryCode + "&appid=" + apiKey;
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, createRequest(), String.class);
        log.info("Coordinates response body:{} ", response.getBody());
        try {
            return objectMapper.readTree(response.getBody());
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private HttpEntity<String> createRequest() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new HttpEntity<>(headers);
    }
}
