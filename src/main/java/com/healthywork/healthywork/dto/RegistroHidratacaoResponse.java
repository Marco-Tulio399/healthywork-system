
package com.healthywork.healthywork.dto;

import java.time.LocalDate;

public record RegistroHidratacaoResponse(
        Long idRegistro,
        Long idUsuario,
        Integer metaMl,
        Integer consumidoMl,
        Integer restanteMl,
        LocalDate dataRegistro
) {}
