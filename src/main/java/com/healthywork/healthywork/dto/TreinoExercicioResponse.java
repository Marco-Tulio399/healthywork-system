
package com.healthywork.healthywork.dto;

public record TreinoExercicioResponse(

        Long idTreinoExercicio,
        Long idExercicio,
        String nomeExercicio,
        String nomeCategoria,
        Integer ordem,
        Integer series,
        Integer repeticoes,
        Integer descansoSegundos

) {
}
