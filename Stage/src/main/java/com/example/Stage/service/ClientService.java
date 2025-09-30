package com.example.Stage.service;

import com.example.Stage.dto.ClientResponse;
import com.example.Stage.entity.Client;
import com.example.Stage.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ClientService {

    private final ClientRepository clientRepository;

    // 🔹 Fonction utilitaire pour mapper un Client en ClientResponse
    private ClientResponse mapToResponse(Client client) {
        return new ClientResponse(
                client.getIdClient(),
                client.getNom(),
                client.getEmail(),
                client.isBlackListed(),
                client.getAgence() != null ? client.getAgence().getIdAgence() : null, // id agence
                client.getAgence() != null ? client.getAgence().getNomAgence() : null // nom agence
        );
    }

    public List<ClientResponse> getAllClients() {
        return clientRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public ClientResponse getClientById(Integer id) {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Client introuvable avec id: " + id));
        return mapToResponse(client);
    }
}
