
package com.healthywork.healthywork;

import com.healthywork.healthywork.dto.PlanoAlimentarRequest;
import com.healthywork.healthywork.dto.PlanoAlimentarResponse;
import com.healthywork.healthywork.entity.PlanoAlimentar;
import com.healthywork.healthywork.service.PlanoAlimentarService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/planos-alimentares")
@CrossOrigin(origins = "*")
public class PlanoAlimentarController {

    private final PlanoAlimentarService planoService;

    public PlanoAlimentarController(PlanoAlimentarService planoService) {
        this.planoService = planoService;
    }

    @PostMapping("/{idUsuario}")
    public PlanoAlimentarResponse cadastrar(
            @PathVariable Long idUsuario,
            @Valid @RequestBody PlanoAlimentarRequest request) {

        PlanoAlimentar plano = planoService.cadastrar(
                idUsuario,
                request.orcamento(),
                request.objetivo()
        );

        return converterParaResponse(plano);
    }

    @GetMapping("/{idUsuario}")
    public List<PlanoAlimentarResponse> listar(
            @PathVariable Long idUsuario) {

        return planoService.listarPorUsuario(idUsuario)
                .stream()
                .map(this::converterParaResponse)
                .toList();
    }

    private PlanoAlimentarResponse converterParaResponse(
            PlanoAlimentar plano) {

        return new PlanoAlimentarResponse(
                plano.getIdPlano(),
                plano.getUsuario().getIdUsuario(),
                plano.getOrcamento(),
                plano.getObjetivo(),
                plano.getDataCriacao()
        );
    }
}
