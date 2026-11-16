
package com.healthywork.healthywork;

import com.healthywork.healthywork.dto.ExercicioRequest;
import com.healthywork.healthywork.dto.ExercicioResponse;
import com.healthywork.healthywork.service.ExercicioService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/exercicios")
@CrossOrigin(origins = "*")
public class ExercicioController {

    private final ExercicioService service;

    public ExercicioController(ExercicioService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ExercicioResponse cadastrar(
            @Valid @RequestBody ExercicioRequest request) {

        return service.cadastrar(request);
    }

    @GetMapping
    public List<ExercicioResponse> listarTodos() {
        return service.listarTodos();
    }

    @GetMapping("/categoria/{idCategoria}")
    public List<ExercicioResponse> listarPorCategoria(
            @PathVariable Long idCategoria) {

        return service.listarPorCategoria(idCategoria);
    }
}
