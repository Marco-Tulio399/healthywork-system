
package com.healthywork.healthywork.service;

import com.healthywork.healthywork.dto.PlanoExercicioRequest;
import com.healthywork.healthywork.dto.PlanoExercicioResponse;
import com.healthywork.healthywork.entity.Exercicio;
import com.healthywork.healthywork.entity.PlanoExercicio;
import com.healthywork.healthywork.entity.Usuario;
import com.healthywork.healthywork.repository.ExercicioRepository;
import com.healthywork.healthywork.repository.PlanoExercicioRepository;
import com.healthywork.healthywork.repository.UsuarioRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;

@Service
public class PlanoExercicioService {

    private final PlanoExercicioRepository repository;
    private final UsuarioRepository usuarioRepository;
    private final ExercicioRepository exercicioRepository;

    public PlanoExercicioService(
            PlanoExercicioRepository repository,
            UsuarioRepository usuarioRepository,
            ExercicioRepository exercicioRepository) {
        this.repository = repository;
        this.usuarioRepository = usuarioRepository;
        this.exercicioRepository = exercicioRepository;
    }

    public PlanoExercicioResponse adicionar(
            Long idUsuario,
            PlanoExercicioRequest request) {

        Usuario usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Usuário não encontrado"));

        Exercicio exercicio = exercicioRepository.findById(request.idExercicio())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Exercício não encontrado"));

        PlanoExercicio plano = new PlanoExercicio();
        plano.setUsuario(usuario);
        plano.setExercicio(exercicio);
        plano.setData(request.data() != null ? request.data() : LocalDate.now());
        plano.setConcluido(false);

        PlanoExercicio salvo = repository.save(plano);

        return converter(salvo);
    }

    public List<PlanoExercicioResponse> listar(Long idUsuario) {

        if (!usuarioRepository.existsById(idUsuario)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Usuário não encontrado");
        }

        return repository.findByUsuarioIdUsuarioOrderByDataDesc(idUsuario)
                .stream()
                .map(this::converter)
                .toList();
    }

    public PlanoExercicioResponse concluir(Long idPlanoExercicio) {

        PlanoExercicio plano = repository.findById(idPlanoExercicio)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Exercício do plano não encontrado"));

        plano.setConcluido(true);

        return converter(repository.save(plano));
    }

    private PlanoExercicioResponse converter(PlanoExercicio plano) {

        return new PlanoExercicioResponse(
                plano.getIdPlanoExercicio(),
                plano.getUsuario().getIdUsuario(),
                plano.getExercicio().getIdExercicio(),
                plano.getExercicio().getNome(),
                plano.getExercicio().getCategoria().getNome(),
                plano.getExercicio().getDuracaoMinutos(),
                plano.getExercicio().getCaloriasEstimadas(),
                plano.getData(),
                plano.getConcluido()
        );
    }
}
