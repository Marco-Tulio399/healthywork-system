
package com.healthywork.healthywork.service;

import com.healthywork.healthywork.dto.TreinoExercicioRequest;
import com.healthywork.healthywork.dto.TreinoExercicioResponse;
import com.healthywork.healthywork.dto.TreinoRequest;
import com.healthywork.healthywork.dto.TreinoResponse;
import com.healthywork.healthywork.entity.Exercicio;
import com.healthywork.healthywork.entity.Treino;
import com.healthywork.healthywork.entity.TreinoExercicio;
import com.healthywork.healthywork.entity.Usuario;
import com.healthywork.healthywork.repository.ExercicioRepository;
import com.healthywork.healthywork.repository.TreinoExercicioRepository;
import com.healthywork.healthywork.repository.TreinoRepository;
import com.healthywork.healthywork.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class TreinoService {

    private final TreinoRepository treinoRepository;
    private final TreinoExercicioRepository treinoExercicioRepository;
    private final UsuarioRepository usuarioRepository;
    private final ExercicioRepository exercicioRepository;

    public TreinoService(
            TreinoRepository treinoRepository,
            TreinoExercicioRepository treinoExercicioRepository,
            UsuarioRepository usuarioRepository,
            ExercicioRepository exercicioRepository
    ) {
        this.treinoRepository = treinoRepository;
        this.treinoExercicioRepository = treinoExercicioRepository;
        this.usuarioRepository = usuarioRepository;
        this.exercicioRepository = exercicioRepository;
    }

    @Transactional
    public TreinoResponse criar(Long idUsuario, TreinoRequest request) {

        Usuario usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Usuário não encontrado"
                ));

        Treino treino = new Treino();
        treino.setUsuario(usuario);
        treino.setNome(request.nome());
        treino.setLocal(request.local());
        treino.setNivel(request.nivel());
        treino.setObjetivo(request.objetivo());
        treino.setDuracaoMinutos(request.duracaoMinutos());

        treino = treinoRepository.save(treino);

        for (TreinoExercicioRequest item : request.exercicios()) {

            Exercicio exercicio = exercicioRepository.findById(item.idExercicio())
                    .orElseThrow(() -> new ResponseStatusException(
                            HttpStatus.NOT_FOUND,
                            "Exercício não encontrado: " + item.idExercicio()
                    ));

            TreinoExercicio treinoExercicio = new TreinoExercicio();
            treinoExercicio.setTreino(treino);
            treinoExercicio.setExercicio(exercicio);
            treinoExercicio.setOrdem(item.ordem());
            treinoExercicio.setSeries(item.series());
            treinoExercicio.setRepeticoes(item.repeticoes());
            treinoExercicio.setDescansoSegundos(item.descansoSegundos());

            treinoExercicioRepository.save(treinoExercicio);
        }

        return converter(treino);
    }

    public List<TreinoResponse> listar(Long idUsuario) {

        if (!usuarioRepository.existsById(idUsuario)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Usuário não encontrado"
            );
        }

        return treinoRepository
                .findByUsuarioIdUsuarioOrderByDataCriacaoDesc(idUsuario)
                .stream()
                .map(this::converter)
                .toList();
    }

    @Transactional
    public void excluir(Long idUsuario, Long idTreino) {

        Treino treino = treinoRepository.findById(idTreino)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Treino não encontrado"
                ));

        if (!treino.getUsuario().getIdUsuario().equals(idUsuario)) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN, "Este treino não pertence ao usuário"
            );
        }

        treinoExercicioRepository.deleteByTreinoIdTreino(idTreino);
        treinoRepository.delete(treino);
    }

    private TreinoResponse converter(Treino treino) {

        List<TreinoExercicioResponse> exercicios =
                treinoExercicioRepository
                        .findByTreinoIdTreinoOrderByOrdemAsc(treino.getIdTreino())
                        .stream()
                        .map(this::converterExercicio)
                        .toList();

        return new TreinoResponse(
                treino.getIdTreino(),
                treino.getUsuario().getIdUsuario(),
                treino.getNome(),
                treino.getLocal(),
                treino.getNivel(),
                treino.getObjetivo(),
                treino.getDuracaoMinutos(),
                treino.getDataCriacao(),
                exercicios
        );
    }

    private TreinoExercicioResponse converterExercicio(
            TreinoExercicio item
    ) {
        return new TreinoExercicioResponse(
                item.getIdTreinoExercicio(),
                item.getExercicio().getIdExercicio(),
                item.getExercicio().getNome(),
                item.getExercicio().getCategoria().getNome(),
                item.getOrdem(),
                item.getSeries(),
                item.getRepeticoes(),
                item.getDescansoSegundos()
        );
    }
}
