package com.example.Stage.controller;

import com.example.Stage.dto.BanquierLoginRequest;
import com.example.Stage.dto.BanquierLoginResponse;
import com.example.Stage.entity.User;
import com.example.Stage.entity.Banquier;
import com.example.Stage.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@RestController
@RequestMapping("/api/login")
@CrossOrigin(origins = "http://localhost:4200")
@RequiredArgsConstructor
public class LoginController {

    private final UserRepository userRepository;

    @PostMapping
    public ResponseEntity<?> login(@RequestBody BanquierLoginRequest request) {
        if (request == null || request.getUsername() == null || request.getPassword() == null) {
            return ResponseEntity.badRequest().body("Username and password required");
        }

        var opt = userRepository.findByUsername(request.getUsername());
        if (opt.isEmpty()) {
            return ResponseEntity.status(401).body("Utilisateur introuvable");
        }

        User user = opt.get();
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        if (!encoder.matches(request.getPassword(), user.getPassword())) {
            return ResponseEntity.status(401).body("Mot de passe incorrect");
        }

        Banquier banquier = user.getBanquier();
        if (banquier == null) {
            return ResponseEntity.status(500).body("Aucun banquier lié à cet utilisateur");
        }

        BanquierLoginResponse response = new BanquierLoginResponse(
                banquier.getIdBanquier().longValue(), // conversion
                banquier.getNom(),
                banquier.getEmail(),
                banquier.getAgence() != null ? banquier.getAgence().getNomAgence() : null
        );

        return ResponseEntity.ok(response);
    }
}
