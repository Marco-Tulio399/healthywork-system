
package com.healthywork.healthywork.service;

import com.healthywork.healthywork.dto.ExercicioRequest;
import com.healthywork.healthywork.dto.ExercicioResponse;
import com.healthywork.healthywork.entity.CategoriaExercicio;
import com.healthywork.healthywork.entity.Exercicio;
import com.healthywork.healthywork.repository.CategoriaExercicioRepository;
import com.healthywork.healthywork.repository.ExercicioRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class ExercicioService {

    private final ExercicioRepository repository;
    private final CategoriaExercicioRepository categoriaRepository;

    public ExercicioService(
            ExercicioRepository repository,
            CategoriaExercicioRepository categoriaRepository) {
        this.repository = repository;
        this.categoriaRepository = categoriaRepository;
    }

    public ExercicioResponse cadastrar(ExercicioRequest request) {

        CategoriaExercicio categoria =
                categoriaRepository.findById(request.idCategoria())
                        .orElseThrow(() -> new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Categoria não encontrada"));

        Exercicio exercicio = new Exercicio();
        exercicio.setCategoria(categoria);
        exercicio.setNome(request.nome().trim());
        exercicio.setDescricao(request.descricao());
        exercicio.setDuracaoMinutos(request.duracaoMinutos());
        exercicio.setCaloriasEstimadas(request.caloriasEstimadas());
        exercicio.setLocal(request.local());

        Exercicio salvo = repository.save(exercicio);

        return converter(salvo);
    }

    public List<ExercicioResponse> listarTodos() {

        return repository.findAll()
                .stream()
                .map(this::converter)
                .toList();
    }

    public List<ExercicioResponse> listarPorCategoria(Long idCategoria) {

        if (!categoriaRepository.existsById(idCategoria)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Categoria não encontrada");
        }

        return repository.findByCategoriaIdCategoria(idCategoria)
                .stream()
                .map(this::converter)
                .toList();
    }

    private ExercicioResponse converter(Exercicio exercicio) {

        return new ExercicioResponse(
                exercicio.getIdExercicio(),
                exercicio.getCategoria().getIdCategoria(),
                exercicio.getCategoria().getNome(),
                exercicio.getNome(),
                exercicio.getDescricao(),
                exercicio.getDuracaoMinutos(),
                exercicio.getCaloriasEstimadas(),
                exercicio.getLocal()
        );
    }
}
