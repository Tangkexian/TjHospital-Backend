package com.javaee.onlinehosbackend.controller;

import com.javaee.onlinehosbackend.dto.WeatherForecast;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.List;

@RestController
@RequestMapping("/api/WeatherForecast")
public class WeatherForecastController {

    private static final String[] SUMMARIES = {
            "Freezing", "Bracing", "Chilly", "Cool", "Mild", "Warm", "Balmy", "Hot", "Sweltering", "Scorching"
    };

    @GetMapping
    @CrossOrigin
    public List<WeatherForecast> getWeatherForecast() {
        Random random = new Random();

        return IntStream.range(1, 6).mapToObj(index -> {
            WeatherForecast forecast = new WeatherForecast();
            forecast.setDate(LocalDate.now().plusDays(index));
            forecast.setTemperatureC(random.nextInt(76) - 20);
            forecast.setSummary(SUMMARIES[random.nextInt(SUMMARIES.length)]);
            return forecast;
        }).collect(Collectors.toList());
    }
}
