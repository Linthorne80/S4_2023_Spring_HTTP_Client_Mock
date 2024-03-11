package com.keyin.http.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.keyin.domain.Airport;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

import static java.net.http.HttpClient.newHttpClient;

public class RESTClient {
    private final String baseURL;

    public RESTClient(String baseURL) {
        this.baseURL = baseURL;
    }

    public List<Airport> getAllAirports() {
        List<Airport> airports;

        try (HttpClient client = newHttpClient()) {
            URI uri = URI.create(baseURL).resolve("/airports");
            HttpRequest request = HttpRequest.newBuilder().uri(uri).build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            handleResponse(response);
            airports = buildAirportListFromResponse(response.body());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException("Error processing HTTP request", e);
        }

        return airports;
    }

    private void handleResponse(HttpResponse<String> response) {
        if (response.statusCode() == 200) {
            System.out.println("***** Response Body: " + response.body());
        } else {
            System.out.println("Error Status Code: " + response.statusCode());
        }
    }

    public List<Airport> buildAirportListFromResponse(String response) {
        List<Airport> airports;

        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
            airports = mapper.readValue(response, new TypeReference<>() {
            });
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error processing JSON response", e);
        }

        return airports;
    }
}
