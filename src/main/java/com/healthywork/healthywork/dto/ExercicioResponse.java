
package com.healthywork.healthywork.dto;

public record ExercicioResponse(
        Long idExercicio,
        Long idCategoria,
        String nomeCategoria,
        String nome,
        String descricao,
        Integer duracaoMinutos,
        Integer caloriasEstimadas,
        String local
) {
}
