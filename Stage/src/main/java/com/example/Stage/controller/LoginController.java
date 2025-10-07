package com.example.Stage.controller;

import com.example.Stage.auth_service.auth.CustomUserDetailsService;
import com.example.Stage.auth_service.auth.JwtUtil;
import com.example.Stage.dto.BanquierLoginRequest;
import com.example.Stage.dto.BanquierLoginResponse;
import com.example.Stage.entity.Banquier;
import com.example.Stage.entity.User;
import com.example.Stage.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/login")
@CrossOrigin(origins = "http://localhost:4200")
@RequiredArgsConstructor
public class LoginController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final CustomUserDetailsService userDetailsService;
    private final UserRepository userRepository;

    /**
     * Authentification de l'utilisateur (banquier) et génération des tokens JWT.
     */
    @PostMapping
    public ResponseEntity<?> login(@RequestBody BanquierLoginRequest request) {
        // Vérification de base
        if (request == null || request.getUsername() == null || request.getPassword() == null) {
            return ResponseEntity.badRequest().body(Map.of("error", "Username and password required"));
        }

        // Vérification existence utilisateur
        var opt = userRepository.findByUsername(request.getUsername());
        if (opt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "Utilisateur introuvable"));
        }

        User user = opt.get();

        // Vérification mot de passe
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        if (!encoder.matches(request.getPassword(), user.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "Mot de passe incorrect"));
        }

        // Vérification banquier associé
        Banquier banquier = user.getBanquier();
        if (banquier == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", "Aucun banquier lié à cet utilisateur"));
        }

        // Génération des tokens JWT
        UserDetails userDetails = userDetailsService.loadUserByUsername(user.getUsername());
        String accessToken = jwtUtil.generateToken(userDetails);
        String refreshToken = jwtUtil.generateRefreshToken(userDetails);

        // Création de la réponse utilisateur
        BanquierLoginResponse response = new BanquierLoginResponse(
                banquier.getIdBanquier().longValue(),
                banquier.getNom(),
                banquier.getEmail(),
                banquier.getAgence() != null ? banquier.getAgence().getNomAgence() : null
        );

        // Retour JSON avec infos + tokens
        return ResponseEntity.ok(Map.of(
                "user", response,
                "accessToken", accessToken,
                "refreshToken", refreshToken
        ));
    }

    /**
     * Renouvellement du token d'accès via le refresh token.
     */
    @PostMapping("/refresh-token")
    public ResponseEntity<?> refreshToken(@RequestBody Map<String, String> request) {
        String refreshToken = request.get("refreshToken");

        if (refreshToken == null || !jwtUtil.isTokenValid(refreshToken)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "Invalid or expired refresh token"));
        }

        String username = jwtUtil.extractUsername(refreshToken);
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        String newAccessToken = jwtUtil.generateToken(userDetails);

        return ResponseEntity.ok(Map.of("accessToken", newAccessToken));
    }
}
