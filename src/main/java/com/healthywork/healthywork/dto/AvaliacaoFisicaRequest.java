
package com.healthywork.healthywork.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public record AvaliacaoFisicaRequest(

        @NotNull
        @DecimalMin("0.01")
        BigDecimal peso,

        @NotNull
        @DecimalMin("0.01")
        BigDecimal altura

) {}
