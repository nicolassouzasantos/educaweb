package com.br.educaweb.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginResponseDTO {
    private String message;
    private String token; // por enquanto um token fake/uuid; depois podemos trocar pra JWT
}