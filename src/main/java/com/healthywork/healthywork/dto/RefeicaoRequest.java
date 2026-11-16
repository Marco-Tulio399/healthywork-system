
package com.healthywork.healthywork.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalTime;

public record RefeicaoRequest(

        @NotBlank
        String tipo,

        @NotNull
        LocalTime horario

) {
}
