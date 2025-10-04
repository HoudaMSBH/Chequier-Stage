package com.example.Stage.controller;

import com.example.Stage.dto.HistoriqueResponse;
import com.example.Stage.service.HistoriqueDemandeService;
import com.example.Stage.service.HistoriqueDemandeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class HistoriqueDemandeController {

    private final HistoriqueDemandeService historiqueService;

    @GetMapping("/api/historiques")
    public List<HistoriqueResponse> getAllHistorique() {
        return historiqueService.getAllHistorique();
    }
}
