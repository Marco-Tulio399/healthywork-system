
package com.healthywork.healthywork;

import com.healthywork.healthywork.dto.CategoriaExercicioRequest;
import com.healthywork.healthywork.dto.CategoriaExercicioResponse;
import com.healthywork.healthywork.service.CategoriaExercicioService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categorias-exercicios")
@CrossOrigin(origins = "*")
public class CategoriaExercicioController {

    private final CategoriaExercicioService service;

    public CategoriaExercicioController(CategoriaExercicioService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CategoriaExercicioResponse cadastrar(
            @Valid @RequestBody CategoriaExercicioRequest request) {

        return service.cadastrar(request);
    }

    @GetMapping
    public List<CategoriaExercicioResponse> listarTodas() {
        return service.listarTodas();
    }
}
