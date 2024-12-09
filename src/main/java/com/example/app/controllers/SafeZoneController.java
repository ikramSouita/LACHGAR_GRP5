package com.example.app.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@RestController
@RequestMapping("/api/safe-zone")
public class SafeZoneController {

    private final RestTemplate restTemplate;

    public SafeZoneController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @PostMapping("/check")
    public ResponseEntity<?> checkSafeZone(@RequestBody Map<String, Double> coordinates) {
        if (!coordinates.containsKey("latitude") || !coordinates.containsKey("longitude")) {
            return ResponseEntity.badRequest().body("Missing latitude or longitude");
        }

        // URL pour appeler Gateway
        String gatewayUrl = "http://localhost:8083/api/flask/check_safe_zone";

        try {
            ResponseEntity<Map> response = restTemplate.postForEntity(gatewayUrl, coordinates, Map.class);
            return ResponseEntity.status(response.getStatusCode()).body(response.getBody());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error: " + e.getMessage());
        }
    }
}
