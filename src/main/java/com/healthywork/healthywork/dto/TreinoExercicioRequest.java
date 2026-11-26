
package com.healthywork.healthywork.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record TreinoExercicioRequest(

        @NotNull
        Long idExercicio,

        @NotNull
        @Min(1)
        Integer ordem,

        @NotNull
        @Min(1)
        Integer series,

        @NotNull
        @Min(1)
        Integer repeticoes,

        @NotNull
        @Min(0)
        Integer descansoSegundos

) {
}
