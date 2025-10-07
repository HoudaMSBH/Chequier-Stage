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
@CrossOrigin(origins = "http://localhost:4200")
public class DemandeChequierController {

    private final DemandeChequierService demandeService;

    @PostMapping
    public DemandeChequierResponse creerDemande(@RequestBody DemandeChequierRequest request) {
        DemandeChequierResponse response = demandeService.creerDemande(request);
        if (!response.isSuccess()) {
            System.out.println("Demande refusée : " + response.getMessage());
        }
        return response;
    }

    @GetMapping("/getAll")
    public List<DemandeChequierResponse> getAllDemandes() {
        return demandeService.getAllDemandes();
    }

    @GetMapping("/{id}")
    public DemandeChequierResponse getDemandeById(@PathVariable Integer id) {
        return demandeService.getDemandeById(id);
    }

    @GetMapping("/client/{clientId}")
    public List<DemandeChequierResponse> getDemandesByClient(@PathVariable Integer clientId) {
        return demandeService.getDemandesByClientId(clientId);
    }

    @PutMapping("/{id}/valider")
    public DemandeChequierResponse validerDemande(@PathVariable Integer id, @RequestParam Integer banquierId) {
        UpdateDemandeRequest req = new UpdateDemandeRequest();
        req.setDemandeId(id);
        req.setAction("VALIDER");
        return demandeService.traiterDemande(req, banquierId);
    }

    public static class RefusRequest {
        public Integer motifId;
        public String motif;
    }

    @PutMapping("/{id}/refuser")
    public DemandeChequierResponse refuserDemande(
            @PathVariable Integer id,
            @RequestBody RefusRequest body,
            @RequestParam Integer banquierId) {

        UpdateDemandeRequest req = new UpdateDemandeRequest();
        req.setDemandeId(id);
        req.setAction("REFUSER");
        if (body != null) {
            if (body.motifId != null) req.setMotifId(body.motifId);
            if (body.motif != null && !body.motif.trim().isEmpty()) req.setMotifLibre(body.motif.trim());
        }
        System.out.println("Motif libre reçu côté back : " + req.getMotifLibre());
        return demandeService.traiterDemande(req, banquierId);
    }

    @PutMapping("/{id}/changer-statut")
    public DemandeChequierResponse changerStatutDemande(@PathVariable Integer id, @RequestParam Integer banquierId) {
        UpdateDemandeRequest req = new UpdateDemandeRequest();
        req.setDemandeId(id);
        req.setAction("CHANGER_STATUT");
        return demandeService.traiterDemande(req, banquierId);
    }
}
