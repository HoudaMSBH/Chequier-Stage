package com.example.Stage.service;

import com.example.Stage.entity.Client;
import com.example.Stage.entity.DemandeChequier;
import com.example.Stage.entity.Notification;
import com.example.Stage.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;

    public void envoyerNotification(Client client, DemandeChequier demande, String message) {
        if (client == null) return;

        Notification notif = new Notification();
        notif.setClient(client);
        notif.setDemande(demande);
        notif.setMessage(message);
        notif.setDateNotification(LocalDateTime.now());

        notificationRepository.save(notif);

        // Optionnel : log pour vérification console
        System.out.println("Notification envoyée à " + client.getNom() + ": " + message);
    }
}
