
package com.healthywork.healthywork.service;

import com.healthywork.healthywork.entity.PlanoAlimentar;
import com.healthywork.healthywork.entity.Refeicao;
import com.healthywork.healthywork.repository.PlanoAlimentarRepository;
import com.healthywork.healthywork.repository.RefeicaoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalTime;
import java.util.List;

@Service
public class RefeicaoService {

    private final RefeicaoRepository refeicaoRepository;
    private final PlanoAlimentarRepository planoRepository;

    public RefeicaoService(
            RefeicaoRepository refeicaoRepository,
            PlanoAlimentarRepository planoRepository) {
        this.refeicaoRepository = refeicaoRepository;
        this.planoRepository = planoRepository;
    }

    public Refeicao cadastrar(Long idPlano, String tipo, LocalTime horario) {

        if (tipo == null || tipo.isBlank() || tipo.length() > 30) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Tipo de refeição inválido");
        }

        if (horario == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Informe o horário da refeição");
        }

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
}
