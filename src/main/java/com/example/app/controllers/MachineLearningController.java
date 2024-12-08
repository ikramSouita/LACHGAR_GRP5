package com.example.app.controllers;

import com.example.app.dtos.AnomalieDTO;
import com.example.app.entities.Position;
import com.example.app.services.MachineLearningService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/machine-learning")
public class MachineLearningController {

    @Autowired
    private MachineLearningService machineLearningService;

    @PostMapping("/analyse")
    public List<AnomalieDTO> analyserPositions(@RequestBody List<Position> positions) {
        return machineLearningService.analyserDonnees(positions);
    }
    @PostMapping("/test")
    public List<AnomalieDTO> testPythonApi() {
        List<Position> positions = List.of(
                new Position(1L, 45.76, 4.84, LocalDateTime.now(), null),
                new Position(2L, 48.85, 2.35, LocalDateTime.now(), null)
        );
        return machineLearningService.analyserDonnees(positions);
    }

}
