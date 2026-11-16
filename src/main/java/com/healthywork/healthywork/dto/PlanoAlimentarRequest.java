
package com.healthywork.healthywork.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record PlanoAlimentarRequest(

        @NotNull
        @DecimalMin("0.01")
        BigDecimal orcamento,

        @NotBlank
        String objetivo

) {
}
