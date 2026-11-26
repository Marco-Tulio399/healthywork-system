
package com.healthywork.healthywork.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.util.List;

public record TreinoRequest(

        @NotBlank
        @Size(max = 100)
        String nome,

        @NotBlank
        @Size(max = 30)
        String local,

        @Size(max = 30)
        String nivel,

        @Size(max = 100)
        String objetivo,

        Integer duracaoMinutos,

        @NotEmpty
        List<@Valid TreinoExercicioRequest> exercicios

) {
}
