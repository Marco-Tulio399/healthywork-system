
package com.healthywork.healthywork.dto;

import java.time.LocalTime;

public record RefeicaoResponse(
        Long idRefeicao,
        Long idPlano,
        String tipo,
        LocalTime horario
) {
}
