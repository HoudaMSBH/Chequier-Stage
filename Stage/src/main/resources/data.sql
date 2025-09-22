-- ==========================
-- Fichier data.sql
-- Initialisation des données pour le projet Chequier
-- ==========================

-- Agences
INSERT INTO Agence (nom_agence, adresse_agence) VALUES
                                                    ('Agence Centre', '10 Rue Nationale, Alger'),
                                                    ('Agence Ouest', '45 Avenue Ahmed, Oran');

-- Statuts (workflow)
INSERT INTO Statut_Demande (libelle, ordre) VALUES
                                                ('En attente', 1),
                                                ('Refusé auto', 2),
                                                ('En validation', 3),
                                                ('Refusé banquier', 4),
                                                ('Validé', 5),
                                                ('Commandé', 6),
                                                ('Prêt', 7),
                                                ('Remis', 8);

-- Motifs de refus
INSERT INTO Motif_Refus (libelle, type_motif) VALUES
                                                  ('Client blacklisté', 'AUTO'),
                                                  ('Compte bloqué', 'AUTO'),
                                                  ('Documents manquants', 'BANQUIER'),
                                                  ('Non solvable', 'BANQUIER'),
                                                  ('Autre', 'BANQUIER');

-- Clients
INSERT INTO Client (nom, email, black_listed, id_agence) VALUES
                                                             ('Ali Ben', 'ali.ben@mail.com', FALSE, 1),
                                                             ('Sara Kaci', 'sara.kaci@mail.com', TRUE, 2);

-- Comptes
INSERT INTO Compte (numero_compte, solde, bloque, id_client) VALUES
                                                                 ('CPT-001', 50000, FALSE, 1),
                                                                 ('CPT-002', 2000, TRUE, 2);

-- Banquiers
INSERT INTO Banquier (nom, email, id_agence) VALUES
                                                 ('Mourad Hamid', 'mourad.hamid@mail.com', 1),
                                                 ('Leila Bouzid', 'leila.bouzid@mail.com', 2);

-- Exemple de demande
INSERT INTO Demande_Chequier (type_chequier, nombre_chequiers, id_client, id_compte, id_agence, id_statut) VALUES
    ('25', 1, 1, 1, 1, 1);

-- Historique (première entrée)
INSERT INTO Historique_Demande (id_demande, id_statut, motif_libelle, type_motif) VALUES
    (1, 1, NULL, NULL);

-- Notification initiale
INSERT INTO Notification (message, id_client, id_demande) VALUES
    ('Votre demande de chéquier est en attente de validation.', 1, 1);