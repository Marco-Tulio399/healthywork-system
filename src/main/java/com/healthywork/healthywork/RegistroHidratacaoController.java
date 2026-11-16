
package com.healthywork.healthywork;

import com.healthywork.healthywork.dto.AdicionarAguaRequest;
import com.healthywork.healthywork.dto.RegistroHidratacaoResponse;
import com.healthywork.healthywork.entity.RegistroHidratacao;
import com.healthywork.healthywork.service.RegistroHidratacaoService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/hidratacao")
public class RegistroHidratacaoController {

    private final RegistroHidratacaoService hidratacaoService;

    public RegistroHidratacaoController(
            RegistroHidratacaoService hidratacaoService) {
        this.hidratacaoService = hidratacaoService;
    }

    @GetMapping("/{idUsuario}")
    public RegistroHidratacaoResponse consultar(
            @PathVariable Long idUsuario) {

        RegistroHidratacao registro =
                hidratacaoService.obterOuCriarRegistro(idUsuario);

        return converterResposta(registro);
    }

    @PostMapping("/{idUsuario}/adicionar")
    public RegistroHidratacaoResponse adicionar(
            @PathVariable Long idUsuario,
            @Valid @RequestBody AdicionarAguaRequest request) {

        RegistroHidratacao registro =
                hidratacaoService.adicionarAgua(
                        idUsuario,
                        request.quantidadeMl()
                );

        return converterResposta(registro);
    }

    private RegistroHidratacaoResponse converterResposta(
            RegistroHidratacao registro) {

        int restante = Math.max(
                0, registro.getMetaMl() - registro.getConsumidoMl());

        return new RegistroHidratacaoResponse(
                registro.getIdRegistro(),
                registro.getUsuario().getIdUsuario(),
                registro.getMetaMl(),
                registro.getConsumidoMl(),
                restante,
                registro.getDataRegistro()
        );
    }
}
