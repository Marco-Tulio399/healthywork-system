
package com.healthywork.healthywork.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record RefeicaoAlimentoRequest(

        @NotNull
        Long idAlimento,

        @NotNull
        @DecimalMin("0.01")
        BigDecimal quantidade

) {
}
