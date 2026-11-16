
package com.healthywork.healthywork.dto;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record PlanoExercicioRequest(

        @NotNull
        Long idExercicio,

        LocalDate data

) {
}
