
package com.healthywork.healthywork.dto;

import java.time.LocalDateTime;
import java.util.List;

public record TreinoResponse(

        Long idTreino,
        Long idUsuario,
        String nome,
        String local,
        String nivel,
        String objetivo,
        Integer duracaoMinutos,
        LocalDateTime dataCriacao,
        List<TreinoExercicioResponse> exercicios

) {
}
