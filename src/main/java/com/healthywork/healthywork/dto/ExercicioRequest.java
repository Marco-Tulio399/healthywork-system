
package com.healthywork.healthywork.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ExercicioRequest(

        @NotNull
        Long idCategoria,

        @NotBlank
        @Size(max = 100)
        String nome,

        String descricao,

        @NotNull
        @Min(1)
        Integer duracaoMinutos,

        @Min(0)
        Integer caloriasEstimadas,

        @Size(max = 30)
        String local

) {
}
