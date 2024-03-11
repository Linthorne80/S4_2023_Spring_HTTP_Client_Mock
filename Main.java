package com.keyin.http.client;

import com.keyin.domain.Airport;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        String baseURL = "http://localhost:3000"; // Update with your actual base URL
        RESTClient restClient = new RESTClient();

        List<Airport> airports = restClient.getAllAirports();

        System.out.println("Airports: " + airports);
    }
}
