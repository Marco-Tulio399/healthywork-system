
package com.healthywork.healthywork.dto;

import java.math.BigDecimal;

public record RefeicaoAlimentoResponse(
        Long idRefeicao,
        Long idAlimento,
        String nomeAlimento,
        BigDecimal quantidade,
        BigDecimal calorias
) {
}
