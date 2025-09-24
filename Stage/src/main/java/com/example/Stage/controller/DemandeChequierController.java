package com.example.Stage.controller;

import com.example.Stage.dto.DemandeChequierRequest;
import com.example.Stage.dto.DemandeChequierResponse;
import com.example.Stage.entity.DemandeChequier;
import com.example.Stage.service.DemandeChequierService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;



import com.example.Stage.dto.DemandeChequierRequest;
import com.example.Stage.dto.DemandeChequierResponse;
import com.example.Stage.service.DemandeChequierService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/demandes")
@RequiredArgsConstructor
public class DemandeChequierController {

    private final DemandeChequierService demandeService;

    //  Créer une nouvelle demande
    @PostMapping
    public DemandeChequierResponse creerDemande(@RequestBody DemandeChequierRequest request) {
        DemandeChequierResponse response = demandeService.creerDemande(request);
        if (!response.isSuccess()) {
            System.out.println("Demande refusée : " + response.getMessage());
        }
        return response;
    }

    // Récupérer toutes les demandes
    @GetMapping
    public List<DemandeChequierResponse> getAllDemandes() {
        return demandeService.getAllDemandes();
    }

    //  Récupérer une demande par ID
    @GetMapping("/{id}")
    public DemandeChequierResponse getDemandeById(@PathVariable Integer id) {
        return demandeService.getDemandeById(id);
    }

    // Récupérer toutes les demandes d'un client
    @GetMapping("/client/{clientId}")
    public List<DemandeChequierResponse> getDemandesByClient(@PathVariable Integer clientId) {
        return demandeService.getDemandesByClientId(clientId);
    }

}

