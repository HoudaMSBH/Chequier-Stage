package com.example.Stage.service;

import com.example.Stage.dto.DemandeChequierRequest;
import com.example.Stage.dto.DemandeChequierResponse;
import com.example.Stage.entity.*;
import com.example.Stage.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor // Lombok : constructeur avec tous les champs final
public class DemandeChequierService {

    private final ClientRepository clientRepo;
    private final CompteRepository compteRepo;
    private final AgenceRepository agenceRepo;
    private final DemandeChequierRepository demandeRepo;
    private final StatutDemandeRepository statutRepo;
    private final HistoriqueDemandeRepository historiqueDemandeRepository;
    private final MotifRefusRepository motifRefusRepository;

    //////////////////////////////////////////////////////////////////////////////
    //  Créer une demande de chéquier
    public DemandeChequierResponse creerDemande(DemandeChequierRequest request) {

        // Vérification client
        Client client = clientRepo.findById(request.getClientId()).orElse(null);
        if (client == null) {
            return DemandeChequierResponse.builder()
                    .success(false)
                    .message("Client introuvable")
                    .build();
        }

        // Vérification compte
        Compte compte = compteRepo.findByNumeroCompte(request.getNumeroCompte()).orElse(null);
        if (compte == null) {
            return DemandeChequierResponse.builder()
                    .success(false)
                    .message("Compte introuvable")
                    .build();
        }

        if (!compte.getClient().getIdClient().equals(request.getClientId())) {
            return DemandeChequierResponse.builder()
                    .success(false)
                    .message("Ce compte n'appartient pas au client")
                    .build();
        }

        // Vérification agence
        Agence agence = request.isUtiliserAgenceClient()
                ? client.getAgence()
                : agenceRepo.findByNomAgence(request.getNomAgenceChoisie()).orElse(null);

        if (agence == null) {
            return DemandeChequierResponse.builder()
                    .success(false)
                    .message("Agence introuvable")
                    .build();
        }

        // Vérification éligibilité
        boolean refusAuto = client.isBlackListed() || compte.getBloque();

        StatutDemande statut;
        if (refusAuto) {
            statut = statutRepo.findByLibelle("Refusé auto").orElse(null);
        } else {
            statut = statutRepo.findByLibelle("En attente").orElse(null);
        }

        // --- Création historique ---
        HistoriqueDemande historique = new HistoriqueDemande();
        historique.setDateChangement(LocalDateTime.now());
        historique.setStatut(statut);

        if (refusAuto) {
            MotifRefus motif = motifRefusRepository.findAll().stream()
                    .filter(m -> "AUTO".equals(m.getTypeMotif()))
                    .findFirst()
                    .orElse(null);

            if (motif != null) {
                historique.setMotif(motif);
                historique.setMotifLibelle(motif.getLibelle());
                historique.setTypeMotif(motif.getTypeMotif());
            }

            historiqueDemandeRepository.save(historique);

            return DemandeChequierResponse.builder()
                    .success(false)
                    .message("Client ou compte non éligible (Blacklisté ou compte bloqué)")
                    .statut(statut != null ? statut.getLibelle() : "Refusé")
                    .clientNom(client.getNom())
                    .numeroCompte(compte.getNumeroCompte())
                    .agenceNom(agence.getNomAgence())
                    .typeChequier(request.getTypeChequier())
                    .build();
        }

        // --- Création de la demande ---
        DemandeChequier demande = new DemandeChequier();
        demande.setClient(client);
        demande.setCompte(compte);
        demande.setDateDemande(LocalDateTime.now());
        demande.setTypeChequier(Integer.parseInt(request.getTypeChequier()));
        demande.setNombreChequiers(request.getNombreChequiers());
        demande.setAgence(agence);
        demande.setStatut(statut);

        DemandeChequier saved = demandeRepo.save(demande);

        // Lier historique à la demande
        historique.setDemande(saved);
        historiqueDemandeRepository.save(historique);

        return DemandeChequierResponse.builder()
                .success(true)
                .message("Demande créée avec succès")
                .demandeId(saved.getIdDemande())
                .clientNom(client.getNom())
                .numeroCompte(compte.getNumeroCompte())
                .typeChequier(saved.getTypeChequier().toString())
                .nombreChequiers(saved.getNombreChequiers())
                .agenceNom(agence.getNomAgence())
                .statut(statut.getLibelle())
                .build();
    }

    //////////////////////////////////////////////////////////////////////////////
    // Récupérer toutes les demandes
    public List<DemandeChequierResponse> getAllDemandes() {
        return demandeRepo.findAll().stream()
                .map(d -> DemandeChequierResponse.builder()
                        .demandeId(d.getIdDemande())
                        .clientNom(d.getClient().getNom())
                        .numeroCompte(d.getCompte().getNumeroCompte())
                        .typeChequier(d.getTypeChequier().toString())
                        .agenceNom(d.getAgence().getNomAgence())
                        .statut(d.getStatut().getLibelle())
                        .success(true)
                        .build())
                .collect(Collectors.toList());
    }

    //////////////////////////////////////////////////////////////////////////////
    // Récupérer une demande par ID
    public DemandeChequierResponse getDemandeById(Integer id) {
        DemandeChequier d = demandeRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Demande introuvable"));

        return DemandeChequierResponse.builder()
                .demandeId(d.getIdDemande())
                .clientNom(d.getClient().getNom())
                .numeroCompte(d.getCompte().getNumeroCompte())
                .typeChequier(d.getTypeChequier().toString())
                .agenceNom(d.getAgence().getNomAgence())
                .statut(d.getStatut().getLibelle())
                .success(true)
                .build();
    }

    //////////////////////////////////////////////////////////////////////////////
    // Récupérer toutes les demandes d'un client
    public List<DemandeChequierResponse> getDemandesByClientId(Integer clientId) {
        List<DemandeChequier> demandes = demandeRepo.findByClientId(clientId);

        return demandes.stream()
                .map(d -> DemandeChequierResponse.builder()
                        .demandeId(d.getIdDemande())
                        .clientNom(d.getClient().getNom())
                        .numeroCompte(d.getCompte().getNumeroCompte())
                        .typeChequier(d.getTypeChequier().toString())
                        .agenceNom(d.getAgence().getNomAgence())
                        .statut(d.getStatut().getLibelle())
                        .success(true)
                        .build())
                .collect(Collectors.toList());
    }
}
