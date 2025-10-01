package com.example.Stage.controller;

import com.example.Stage.dto.HistoriqueResponse;
import com.example.Stage.service.HistoriqueDemandeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/historiques")
@RequiredArgsConstructor
public class HistoriqueDemandeController {

    private final HistoriqueDemandeService historiqueService;

    @GetMapping
    public List<HistoriqueResponse> getAllHistorique() {
        return historiqueService.getAllHistorique();
    }
}
