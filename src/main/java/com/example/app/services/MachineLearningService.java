package com.example.app.services;

import com.example.app.dtos.AnomalieDTO;
import com.example.app.entities.Position;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class MachineLearningService {

    private final RestTemplate restTemplate;

    public MachineLearningService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<AnomalieDTO> analyserDonnees(List<Position> positions) {
        String pythonApiUrl = "http://localhost:5000/analyse"; // URL du module Python

        HttpEntity<List<Position>> requestEntity = new HttpEntity<>(positions);

        ResponseEntity<List<AnomalieDTO>> response = restTemplate.exchange(
                pythonApiUrl,
                HttpMethod.POST,
                requestEntity,
                new ParameterizedTypeReference<List<AnomalieDTO>>() {}
        );

        return response.getBody();
    }

}
