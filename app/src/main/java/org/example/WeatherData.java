package org.example;

public class WeatherData {
    public String forecast;
    public int temperatureF;
    public double temperatureC;
    public String iconName;

    public WeatherData(String forecast, int temperatureF) {
        this.forecast = forecast;
        this.temperatureF = temperatureF;
        this.temperatureC = (temperatureF - 32) * 5.0 / 9.0;
        this.iconName = determineIcon(forecast);
    }

    private String determineIcon(String forecast) {
        forecast = forecast.toLowerCase();
        if (forecast.contains("rain")) return "rain.png";
        if (forecast.contains("snow")) return "snow.png";
        if (forecast.contains("cloud")) return "cloud.png";
        if (forecast.contains("sun") || forecast.contains("clear")) return "sun.png";
        return "default.png";
    }
}
