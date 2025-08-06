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
        String pointsJson = getJson(pointsURL);
    
        // 1. Get observationStations URL
        String observationStations = extractValue(pointsJson, "\"observationStations\": \"", "\"");
        if (observationStations.isEmpty()) throw new Exception("observationStations URL not found");
        System.out.println(observationStations);
    
        // 2. Get observationStations JSON and extract first station ID
        String stationsJson = getJson(observationStations);
        String firstStationId = extractValue(stationsJson, "\"stationIdentifier\": \"", "\"");
        System.out.println("First station: " + firstStationId);
    
        // 3. Get latest observation for that station
        String observationUrl = "https://api.weather.gov/stations/" + firstStationId + "/observations/latest";
        String observationJson = getJson(observationUrl);
    
        // 4. Extract temperature (Celsius) and convert to Fahrenheit
        String tempCStr = extractValue(observationJson, "\"temperature\": ", "}");
        System.out.println(tempCStr);
        String CStr = extractValue(tempCStr, "\"value\": ", ",");

    
        System.out.print(CStr);
        double tempC = Double.parseDouble(CStr.trim());
        int temperatureF = (int) Math.round((tempC * 9 / 5) + 32);
    
        System.out.println("Temperature: " + temperatureF + "°F");
    
        return new WeatherData(firstStationId, temperatureF);
    }
    
    

    private String getJson(String urlStr) throws Exception {
        URL url = new URL(urlStr);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("User-Agent", "MyJavaApp/1.0 " + email);

        InputStream is = con.getInputStream();
        byte[] data = is.readAllBytes();
        is.close();
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
