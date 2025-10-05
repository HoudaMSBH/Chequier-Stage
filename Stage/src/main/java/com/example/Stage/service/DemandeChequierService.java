package com.example.Stage.service;

import com.example.Stage.dto.DemandeChequierRequest;
import com.example.Stage.dto.DemandeChequierResponse;
import com.example.Stage.dto.UpdateDemandeRequest;
import com.example.Stage.entity.*;
import com.example.Stage.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DemandeChequierService {

    private final ClientRepository clientRepo;
    private final CompteRepository compteRepo;
    private final AgenceRepository agenceRepo;
    private final DemandeChequierRepository demandeRepo;
    private final StatutDemandeRepository statutRepo;
    private final HistoriqueDemandeRepository historiqueDemandeRepository;
    private final MotifRefusRepository motifRefusRepository;
    private final BanquierRepository banquierRepo;


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
    // --- Récupérer toutes les demandes ---
    public List<DemandeChequierResponse> getAllDemandes() {
        return demandeRepo.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // --- Récupérer demandes par client ---
    public List<DemandeChequierResponse> getDemandesByClientId(Integer clientId) {
        return demandeRepo.findByClientId(clientId).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // --- Récupérer demande par id ---
    public DemandeChequierResponse getDemandeById(Integer id) {
        return demandeRepo.findById(id)
                .map(this::mapToResponse)
                .orElse(null);
    }

    // --- Traiter une demande (valider, refuser, changer statut) ---
    public DemandeChequierResponse traiterDemande(UpdateDemandeRequest request, Integer banquierId) {
        System.out.println("MotifLibre reçu : " + request.getMotifLibre());
        Optional<DemandeChequier> optDemande = demandeRepo.findById(request.getDemandeId());
        if (!optDemande.isPresent()) {
            return DemandeChequierResponse.builder()
                    .success(false)
                    .message("Demande non trouvée")
                    .build();
        }

        DemandeChequier demande = optDemande.get();

        Banquier banquier = banquierRepo.findById(banquierId).orElse(null);

        switch (request.getAction()) {

            case "VALIDER":
                StatutDemande statutCommande = statutRepo.findByLibelle("Commandé").orElse(null);
                if (statutCommande != null) {
                    demande.setStatut(statutCommande);
                    demandeRepo.save(demande);

                    HistoriqueDemande histValider = new HistoriqueDemande();
                    histValider.setDateChangement(LocalDateTime.now());
                    histValider.setDemande(demande);
                    histValider.setStatut(statutCommande);
                    histValider.setBanquier(banquier);
                    historiqueDemandeRepository.save(histValider);


                }
                break;

            case "REFUSER":
                StatutDemande statutRefuse = statutRepo.findByLibelle("Refusé banquier").orElse(null);

                if (statutRefuse != null) {
                    demande.setStatut(statutRefuse);
                    demandeRepo.save(demande);

                    HistoriqueDemande histRefus = new HistoriqueDemande();
                    histRefus.setDateChangement(LocalDateTime.now());
                    histRefus.setDemande(demande);
                    histRefus.setStatut(statutRefuse);
                    histRefus.setBanquier(banquier);

                    if (request.getMotifLibre() != null && !request.getMotifLibre().trim().isEmpty()) {
                        histRefus.setMotif(null);
                        histRefus.setMotifLibelle(null);
                        histRefus.setMotifLibre(request.getMotifLibre().trim());
                        histRefus.setTypeMotif("BANQUIER");
                    } else if (request.getMotifId() != null && request.getMotifId() > 0) {
                        motifRefusRepository.findById(request.getMotifId()).ifPresent(m -> {
                            histRefus.setMotif(m);
                            histRefus.setMotifLibelle(m.getLibelle());
                            histRefus.setMotifLibre(null);
                            histRefus.setTypeMotif(m.getTypeMotif());
                        });
                    } else {
                        histRefus.setMotif(null);
                        histRefus.setMotifLibelle(null);
                        histRefus.setMotifLibre("Motif non spécifié");
                        histRefus.setTypeMotif("BANQUIER");
                    }

                    historiqueDemandeRepository.save(histRefus);



                    return DemandeChequierResponse.builder()
                            .demandeId(demande.getIdDemande())
                            .clientNom(demande.getClient().getNom())
                            .numeroCompte(demande.getCompte().getNumeroCompte())
                            .typeChequier(demande.getTypeChequier() + " chèques")
                            .nombreChequiers(demande.getNombreChequiers())
                            .agenceNom(demande.getAgence().getNomAgence())
                            .statut(statutRefuse.getLibelle())
                            .success(true)
                            .build();
                }
                break;

            case "CHANGER_STATUT":
                String current = demande.getStatut().getLibelle();
                StatutDemande nouveauStatut = null;
                if ("Commandé".equals(current)) {
                    nouveauStatut = statutRepo.findByLibelle("Prêt").orElse(null);
                } else if ("Prêt".equals(current)) {
                    nouveauStatut = statutRepo.findByLibelle("Remis").orElse(null);
                }

                if (nouveauStatut != null) {
                    demande.setStatut(nouveauStatut);
                    demandeRepo.save(demande);

                    HistoriqueDemande histChangement = new HistoriqueDemande();
                    histChangement.setDateChangement(LocalDateTime.now());
                    histChangement.setDemande(demande);
                    histChangement.setStatut(nouveauStatut);
                    histChangement.setBanquier(banquier);
                    historiqueDemandeRepository.save(histChangement);


                }
                break;
        }

        return mapToResponse(demande);
    }

    // --- Mapper pour DTO ---
    private DemandeChequierResponse mapToResponse(DemandeChequier demande) {
        String clientNom = demande.getClient() != null ? demande.getClient().getNom() : null;
        String clientEmail = demande.getClient() != null ? demande.getClient().getEmail() : null;
        Boolean clientBlackListed = demande.getClient() != null ? demande.getClient().isBlackListed() : false;
        String numeroCompte = demande.getCompte() != null ? demande.getCompte().getNumeroCompte() : null;
        BigDecimal solde = (demande.getCompte() != null && demande.getCompte().getSolde() != null)
                ? demande.getCompte().getSolde()
                : BigDecimal.ZERO;
        Boolean compteBloque = demande.getCompte() != null ? demande.getCompte().getBloque() : false;
        String agenceNom = demande.getAgence() != null ? demande.getAgence().getNomAgence() : null;
        String agenceClient = (demande.getClient() != null && demande.getClient().getAgence() != null)
                ? demande.getClient().getAgence().getNomAgence()
                : null;

        return DemandeChequierResponse.builder()
                .demandeId(demande.getIdDemande())
                .clientNom(clientNom)
                .clientEmail(clientEmail)
                .clientBlackListed(clientBlackListed)
                .numeroCompte(numeroCompte)
                .solde(solde)
                .compteBloque(compteBloque)
                .typeChequier(demande.getTypeChequier() + " chèques")
                .nombreChequiers(demande.getNombreChequiers())
                .agenceNom(agenceNom)
                .agenceClient(agenceClient)
                .dateDemande(demande.getDateDemande())
                .statut(demande.getStatut() != null ? demande.getStatut().getLibelle() : null)
                .success(true)
                .build();
    }
}
