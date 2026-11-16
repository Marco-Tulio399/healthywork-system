
package com.healthywork.healthywork.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record AvaliacaoFisicaResponse(
        Long idAvaliacao,
        Long idUsuario,
        BigDecimal peso,
        BigDecimal altura,
        BigDecimal imc,
        String classificacao,
        LocalDateTime dataAvaliacao
) {}
