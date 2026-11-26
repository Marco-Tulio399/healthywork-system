
package com.healthywork.healthywork;

import com.healthywork.healthywork.dto.TreinoRequest;
import com.healthywork.healthywork.dto.TreinoResponse;
import com.healthywork.healthywork.service.TreinoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios/{idUsuario}/treinos")
@CrossOrigin(origins = "*")
public class TreinoController {

    private final TreinoService treinoService;

    public TreinoController(TreinoService treinoService) {
        this.treinoService = treinoService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TreinoResponse criar(
            @PathVariable Long idUsuario,
            @Valid @RequestBody TreinoRequest request
    ) {
        return treinoService.criar(idUsuario, request);
    }

    @GetMapping
    public List<TreinoResponse> listar(
            @PathVariable Long idUsuario
    ) {
        return treinoService.listar(idUsuario);
    }

    @DeleteMapping("/{idTreino}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(
            @PathVariable Long idUsuario,
            @PathVariable Long idTreino
    ) {
        treinoService.excluir(idUsuario, idTreino);
    }
}
