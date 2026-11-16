
package com.healthywork.healthywork.service;

import com.healthywork.healthywork.dto.CategoriaExercicioRequest;
import com.healthywork.healthywork.dto.CategoriaExercicioResponse;
import com.healthywork.healthywork.entity.CategoriaExercicio;
import com.healthywork.healthywork.repository.CategoriaExercicioRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriaExercicioService {

    private final CategoriaExercicioRepository repository;

    public CategoriaExercicioService(CategoriaExercicioRepository repository) {
        this.repository = repository;
    }

    public CategoriaExercicioResponse cadastrar(CategoriaExercicioRequest request) {

        CategoriaExercicio categoria = new CategoriaExercicio();
        categoria.setNome(request.nome().trim());

        CategoriaExercicio salva = repository.save(categoria);

        return converter(salva);
    }

    public List<CategoriaExercicioResponse> listarTodas() {

        return repository.findAll()
                .stream()
                .map(this::converter)
                .toList();
    }

    private CategoriaExercicioResponse converter(CategoriaExercicio categoria) {

        return new CategoriaExercicioResponse(
                categoria.getIdCategoria(),
                categoria.getNome()
        );
    }
}
