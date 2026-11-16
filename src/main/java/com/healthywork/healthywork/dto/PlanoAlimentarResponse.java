
package com.healthywork.healthywork.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record PlanoAlimentarResponse(
        Long idPlano,
        Long idUsuario,
        BigDecimal orcamento,
        String objetivo,
        LocalDateTime dataCriacao
) {
}
