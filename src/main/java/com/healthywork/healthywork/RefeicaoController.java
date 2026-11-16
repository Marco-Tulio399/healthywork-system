
package com.healthywork.healthywork;

import com.healthywork.healthywork.dto.RefeicaoRequest;
import com.healthywork.healthywork.dto.RefeicaoResponse;
import com.healthywork.healthywork.entity.Refeicao;
import com.healthywork.healthywork.service.RefeicaoService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/refeicoes")
@CrossOrigin(origins = "*")
public class RefeicaoController {

    private final RefeicaoService refeicaoService;

    public RefeicaoController(RefeicaoService refeicaoService) {
        this.refeicaoService = refeicaoService;
    }

    @PostMapping("/{idPlano}")
    public RefeicaoResponse cadastrar(
            @PathVariable Long idPlano,
            @Valid @RequestBody RefeicaoRequest request) {

        Refeicao refeicao = refeicaoService.cadastrar(
                idPlano,
                request.tipo(),
                request.horario()
        );

        return converterParaResponse(refeicao);
    }

    @GetMapping("/{idPlano}")
    public List<RefeicaoResponse> listar(@PathVariable Long idPlano) {

        return refeicaoService.listarPorPlano(idPlano)
                .stream()
                .map(this::converterParaResponse)
                .toList();
    }

    private RefeicaoResponse converterParaResponse(Refeicao refeicao) {

        return new RefeicaoResponse(
                refeicao.getIdRefeicao(),
                refeicao.getPlanoAlimentar().getIdPlano(),
                refeicao.getTipo(),
                refeicao.getHorario()
        );
    }
}
