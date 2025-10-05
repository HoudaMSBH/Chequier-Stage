package com.example.Stage.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BanquierLoginRequest {
    private String username;
    private String password;
}
