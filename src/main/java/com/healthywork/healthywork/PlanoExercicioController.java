
package com.healthywork.healthywork;

import com.healthywork.healthywork.dto.PlanoExercicioRequest;
import com.healthywork.healthywork.dto.PlanoExercicioResponse;
import com.healthywork.healthywork.service.PlanoExercicioService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios/{idUsuario}/exercicios")
@CrossOrigin(origins = "*")
public class PlanoExercicioController {

    private final PlanoExercicioService service;

    public PlanoExercicioController(PlanoExercicioService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PlanoExercicioResponse adicionar(
            @PathVariable Long idUsuario,
            @Valid @RequestBody PlanoExercicioRequest request) {

        return service.adicionar(idUsuario, request);
    }

    @GetMapping
    public List<PlanoExercicioResponse> listar(
            @PathVariable Long idUsuario) {

        return service.listar(idUsuario);
    }

    @PatchMapping("/{idPlanoExercicio}/concluir")
    public PlanoExercicioResponse concluir(
            @PathVariable Long idPlanoExercicio) {

        return service.concluir(idPlanoExercicio);
    }
}
