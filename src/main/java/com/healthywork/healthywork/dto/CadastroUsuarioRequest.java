
package com.healthywork.healthywork.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;

public record CadastroUsuarioRequest(

        @NotBlank
        @Size(max = 100)
        String nome,

        @NotBlank
        @Email
        @Size(max = 150)
        String email,

        @NotBlank
        @Size(min = 8, max = 100)
        String senha,

        LocalDate dataNascimento,

        String sexo
) {}
