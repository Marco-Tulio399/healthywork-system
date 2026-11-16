
package com.healthywork.healthywork;

import com.healthywork.healthywork.dto.AvaliacaoFisicaRequest;
import com.healthywork.healthywork.dto.AvaliacaoFisicaResponse;
import com.healthywork.healthywork.entity.AvaliacaoFisica;
import com.healthywork.healthywork.service.AvaliacaoFisicaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/avaliacoes")
public class AvaliacaoFisicaController {

    private final AvaliacaoFisicaService avaliacaoService;

    public AvaliacaoFisicaController(
            AvaliacaoFisicaService avaliacaoService) {
        this.avaliacaoService = avaliacaoService;
    }

    @PostMapping("/{idUsuario}")
    @ResponseStatus(HttpStatus.CREATED)
    public AvaliacaoFisicaResponse cadastrar(
            @PathVariable Long idUsuario,
            @Valid @RequestBody AvaliacaoFisicaRequest request) {

        AvaliacaoFisica avaliacao =
                avaliacaoService.cadastrarAvaliacao(
                        idUsuario,
                        request.peso(),
                        request.altura()
                );

        return converterResposta(avaliacao);
    }

    @GetMapping("/{idUsuario}")
    public List<AvaliacaoFisicaResponse> listar(
            @PathVariable Long idUsuario) {

        return avaliacaoService.listarAvaliacoes(idUsuario)
                .stream()
                .map(this::converterResposta)
                .toList();
    }

    private AvaliacaoFisicaResponse converterResposta(
            AvaliacaoFisica avaliacao) {

        return new AvaliacaoFisicaResponse(
                avaliacao.getIdAvaliacao(),
                avaliacao.getUsuario().getIdUsuario(),
                avaliacao.getPeso(),
                avaliacao.getAltura(),
                avaliacao.getImc(),
                avaliacao.getClassificacao(),
                avaliacao.getDataAvaliacao()
        );
    }
}
