
package com.healthywork.healthywork.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CategoriaExercicioRequest(

        @NotBlank
        @Size(max = 50)
        String nome

) {
}
