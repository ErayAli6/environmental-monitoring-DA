package bg.uni.plovdiv.Controller;

import bg.uni.plovdiv.Service.GetAirPollutionData;
import bg.uni.plovdiv.Service.GetSolarIrradiance;
import bg.uni.plovdiv.Service.GetWeatherData;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/monitor")
public class EnvironmentalMonitoringController {

    private final GetAirPollutionData getAirPollutionData;

    private final GetSolarIrradiance getSolarIrradiance;

    private final GetWeatherData getWeatherData;

    @GetMapping("/air-pollution")
    public String airPollution() {
        return "air-pollution";
    }

    @GetMapping("/solar-irradiance")
    public String solarIrradiance() {
        return "solar-irradiance";
    }

    @GetMapping("/weather")
    public String weather() {
        return "weather";
    }

    @PostMapping("/air-pollution")
    public String fetchAirPollutionData(@RequestParam String zipCode, @RequestParam String countryCode, Model model) {
        String airPollution = getAirPollutionData.execute(zipCode, countryCode);
        model.addAttribute("airPollution", airPollution);
        return "air-pollution";
    }

    @PostMapping("/solar-irradiance")
    public String fetchSolarIrradiance(@RequestParam String zipCode, @RequestParam String countryCode, @RequestParam LocalDate date, Model model) {
        String solarIrradiance = getSolarIrradiance.execute(zipCode, countryCode, date);
        model.addAttribute("solarIrradiance", solarIrradiance);
        return "solar-irradiance";
    }

    @PostMapping("/weather")
    public String fetchWeatherData(@RequestParam String zipCode, @RequestParam String countryCode, Model model) {
        String weather = getWeatherData.execute(zipCode, countryCode);
        model.addAttribute("weather", weather);
        return "weather";
    }
}
