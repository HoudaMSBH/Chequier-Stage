package com.example.Stage.controller;

import com.example.Stage.dto.DemandeChequierRequest;
import com.example.Stage.dto.DemandeChequierResponse;
import com.example.Stage.dto.UpdateDemandeRequest;
import com.example.Stage.service.DemandeChequierService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/demandes")
@RequiredArgsConstructor
public class DemandeChequierController {

    private final DemandeChequierService demandeService;

    // Créer une nouvelle demande
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

    // Récupérer une demande par ID
    @GetMapping("/{id}")
    public DemandeChequierResponse getDemandeById(@PathVariable Integer id) {
        return demandeService.getDemandeById(id);
    }

    // Récupérer toutes les demandes d'un client
    @GetMapping("/client/{clientId}")
    public List<DemandeChequierResponse> getDemandesByClient(@PathVariable Integer clientId) {
        return demandeService.getDemandesByClientId(clientId);
    }



    // Valider : PUT /api/demandes/{id}/valider
    @PutMapping("/{id}/valider")
    public DemandeChequierResponse validerDemande(@PathVariable Integer id) {
        UpdateDemandeRequest req = new UpdateDemandeRequest();
        req.setDemandeId(id);
        req.setAction("VALIDER");
        Integer banquierId = 1; // TODO: récupérer l'id réel
        return demandeService.traiterDemande(req, banquierId);
    }

    // Refuser : PUT /api/demandes/{id}/refuser
    // Body attendu (JSON) : { "motifId": 2 }  ou { "motif": "Motif libre ..." }
    public static class RefusRequest {
        public Integer motifId;
        public String motif; // motif libre
        // Jackson va binder les champs publics automatiquement
    }

    @PutMapping("/{id}/refuser")
    public DemandeChequierResponse refuserDemande(@PathVariable Integer id, @RequestBody RefusRequest body) {
        UpdateDemandeRequest req = new UpdateDemandeRequest();
        req.setDemandeId(id);
        req.setAction("REFUSER");
        if (body != null) {
            if (body.motifId != null) req.setMotifId(body.motifId);
            if (body.motif != null && !body.motif.trim().isEmpty()) req.setMotifLibre(body.motif.trim());
        }
        Integer banquierId = 1; // TODO: récupérer l'id réel
        return demandeService.traiterDemande(req, banquierId);
    }

    // Changer statut : PUT /api/demandes/{id}/changer-statut
    @PutMapping("/{id}/changer-statut")
    public DemandeChequierResponse changerStatutDemande(@PathVariable Integer id) {
        UpdateDemandeRequest req = new UpdateDemandeRequest();
        req.setDemandeId(id);
        req.setAction("CHANGER_STATUT");
        Integer banquierId = 1; // TODO: récupérer l'id réel
        return demandeService.traiterDemande(req, banquierId);
    }
}
