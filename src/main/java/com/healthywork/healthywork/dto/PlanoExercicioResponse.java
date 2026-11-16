
package com.healthywork.healthywork.dto;

import java.time.LocalDate;

public record PlanoExercicioResponse(
        Long idPlanoExercicio,
        Long idUsuario,
        Long idExercicio,
        String nomeExercicio,
        String categoria,
        Integer duracaoMinutos,
        Integer caloriasEstimadas,
        LocalDate data,
        Boolean concluido
) {
}
