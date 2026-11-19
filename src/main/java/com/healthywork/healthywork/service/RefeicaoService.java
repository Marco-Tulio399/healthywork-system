
package com.healthywork.healthywork.service;

import com.healthywork.healthywork.entity.PlanoAlimentar;
import com.healthywork.healthywork.entity.Refeicao;
import com.healthywork.healthywork.repository.PlanoAlimentarRepository;
import com.healthywork.healthywork.repository.RefeicaoAlimentoRepository;
import com.healthywork.healthywork.repository.RefeicaoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalTime;
import java.util.List;

@Service
public class RefeicaoService {

    private final RefeicaoRepository refeicaoRepository;
    private final PlanoAlimentarRepository planoRepository;
    private final RefeicaoAlimentoRepository refeicaoAlimentoRepository;

    public RefeicaoService(
            RefeicaoRepository refeicaoRepository,
            PlanoAlimentarRepository planoRepository,
            RefeicaoAlimentoRepository refeicaoAlimentoRepository) {

        this.refeicaoRepository = refeicaoRepository;
        this.planoRepository = planoRepository;
        this.refeicaoAlimentoRepository = refeicaoAlimentoRepository;
    }

    public Refeicao cadastrar(Long idPlano, String tipo, LocalTime horario) {

        validarDados(tipo, horario);

        PlanoAlimentar plano = planoRepository.findById(idPlano)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Plano alimentar não encontrado"));

        Refeicao refeicao = new Refeicao();
        refeicao.setPlanoAlimentar(plano);
        refeicao.setTipo(tipo);
        refeicao.setHorario(horario);

        return refeicaoRepository.save(refeicao);
    }

    public List<Refeicao> listarPorPlano(Long idPlano) {

        if (!planoRepository.existsById(idPlano)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Plano alimentar não encontrado");
        }

        return refeicaoRepository.findByPlanoAlimentarIdPlano(idPlano);
    }

    @Transactional
    public Refeicao atualizar(Long idRefeicao, String tipo, LocalTime horario) {

        validarDados(tipo, horario);

        Refeicao refeicao = refeicaoRepository.findById(idRefeicao)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Refeição não encontrada"));

        refeicao.setTipo(tipo);
        refeicao.setHorario(horario);

        return refeicaoRepository.save(refeicao);
    }

    @Transactional
    public void excluir(Long idRefeicao) {

        Refeicao refeicao = refeicaoRepository.findById(idRefeicao)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Refeição não encontrada"));

        refeicaoAlimentoRepository.deleteByRefeicaoIdRefeicao(idRefeicao);

        refeicaoRepository.delete(refeicao);
    }

    private void validarDados(String tipo, LocalTime horario) {

        if (tipo == null || tipo.isBlank() || tipo.length() > 30) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Tipo de refeição inválido");
        }

        if (horario == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Informe o horário da refeição");
        }
    }
}
