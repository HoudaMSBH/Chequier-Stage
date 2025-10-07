package com.example.Stage.auth_service.auth;


import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Data
@NoArgsConstructor
public class JwtResponse {
    private String token;
    public JwtResponse(String token) {
        this.token = token;
    }
}
