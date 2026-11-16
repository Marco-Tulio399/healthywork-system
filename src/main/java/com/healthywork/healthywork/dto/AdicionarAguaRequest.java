
package com.healthywork.healthywork.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record AdicionarAguaRequest(

        @NotNull
        @Min(1)
        Integer quantidadeMl

) {}
