package br.com.park.parkCheck.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record AuthRequestDTO (
    @NotBlank(message = "O campo username é obrigatório")
    String username,
    @NotBlank(message = "O campo password é obrigatório")
    @Size(min = 8, message = "A senha deve ter no mínimo 8 caracteres")
    String password
){

}
