package org.example;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class WeatherFetcher {
    private final String lat;
    private final String lon;
    private final String email;

    public WeatherFetcher(String lat, String lon, String email) {
        this.lat = lat;
        this.lon = lon;
        this.email = email;
    }

    public WeatherData fetchWeather() throws Exception {
        String pointsURL = "https://api.weather.gov/points/" + lat + "," + lon;
        String pointsJson = getJson(pointsURL); //performs le get request to get points urls :)
        String forecastUrl = extractValue(pointsJson, "\"forecast\":\"", "\""); //get the forecast url to call
        if (forecastUrl.isEmpty()) {
            throw new Exception("Forecast URL not found from getJson(url)");
        }

        String forecastJson = getJson(forecastUrl); // get request the forcast url.
        String periods = extractValue(forecastJson, "\"periods\":[{", "}]"); //extracts the period json values

        int temperatureF = Integer.parseInt(extractValue(periods, "\"temperature\":", ",")); // extract temperature from periods
        String forecast = extractValue(periods, "\"shortForecast\":\"", "\""); //extract forecast from periods

        return new WeatherData(forecast, temperatureF);
    }

    private String getJson(String urlStr) throws Exception {
        URL url = new URL(urlStr);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("User-Agent", "MyJavaApp/1.0 " + email);

        InputStream is = con.getInputStream();
        byte[] data = is.readAllBytes();
        is.close();
        con.disconnect();

        return new String(data, StandardCharsets.UTF_8);
    }

    private String extractValue(String text, String start, String end) {
        int s = text.indexOf(start);
        if (s == -1) return "";
        s += start.length();
        int e = text.indexOf(end, s);
        if (e == -1) return "";
        return text.substring(s, e);
    }
}
