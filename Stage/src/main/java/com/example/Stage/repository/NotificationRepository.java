package com.example.Stage.repository;

import com.example.Stage.entity.Notification;
import com.example.Stage.entity.Client;
import com.example.Stage.entity.DemandeChequier;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Integer> {

    // Récupérer toutes les notifications d’un client
    List<Notification> findByClient(Client client);

    // Récupérer toutes les notifications liées à une demande
    List<Notification> findByDemande(DemandeChequier demande);
}
