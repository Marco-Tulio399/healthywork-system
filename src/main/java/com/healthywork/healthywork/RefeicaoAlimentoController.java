
package com.healthywork.healthywork;

import com.healthywork.healthywork.dto.RefeicaoAlimentoRequest;
import com.healthywork.healthywork.dto.RefeicaoAlimentoResponse;
import com.healthywork.healthywork.service.RefeicaoAlimentoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/refeicoes")
@CrossOrigin(origins = "*")
public class RefeicaoAlimentoController {

    private final RefeicaoAlimentoService service;

    public RefeicaoAlimentoController(RefeicaoAlimentoService service) {
        this.service = service;
    }

    // Adicionar alimento à refeição
    @PostMapping("/{idRefeicao}/alimentos")
    @ResponseStatus(HttpStatus.CREATED)
    public RefeicaoAlimentoResponse adicionar(
            @PathVariable Long idRefeicao,
            @Valid @RequestBody RefeicaoAlimentoRequest request) {

        return service.adicionar(idRefeicao, request);
    }

    // Listar alimentos da refeição
    @GetMapping("/{idRefeicao}/alimentos")
    public List<RefeicaoAlimentoResponse> listar(
            @PathVariable Long idRefeicao) {

        return service.listar(idRefeicao);
    }

    // Atualizar quantidade de um alimento
    @PutMapping("/{idRefeicao}/alimentos/{idAlimento}")
    public RefeicaoAlimentoResponse atualizar(
            @PathVariable Long idRefeicao,
            @PathVariable Long idAlimento,
            @Valid @RequestBody RefeicaoAlimentoRequest request) {

        return service.atualizar(
                idRefeicao,
                idAlimento,
                request.quantidade()
        );
    }

    // Remover alimento da refeição
    @DeleteMapping("/{idRefeicao}/alimentos/{idAlimento}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(
            @PathVariable Long idRefeicao,
            @PathVariable Long idAlimento) {

        service.excluir(idRefeicao, idAlimento);
    }
}
