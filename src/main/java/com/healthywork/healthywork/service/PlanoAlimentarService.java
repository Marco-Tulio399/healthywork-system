
package com.healthywork.healthywork.service;

import com.healthywork.healthywork.entity.PlanoAlimentar;
import com.healthywork.healthywork.entity.Refeicao;
import com.healthywork.healthywork.entity.Usuario;
import com.healthywork.healthywork.repository.PlanoAlimentarRepository;
import com.healthywork.healthywork.repository.RefeicaoAlimentoRepository;
import com.healthywork.healthywork.repository.RefeicaoRepository;
import com.healthywork.healthywork.repository.UsuarioRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.List;

@Service
public class PlanoAlimentarService {

    private final PlanoAlimentarRepository planoRepository;
    private final UsuarioRepository usuarioRepository;
    private final RefeicaoRepository refeicaoRepository;
    private final RefeicaoAlimentoRepository refeicaoAlimentoRepository;

    public PlanoAlimentarService(
            PlanoAlimentarRepository planoRepository,
            UsuarioRepository usuarioRepository,
            RefeicaoRepository refeicaoRepository,
            RefeicaoAlimentoRepository refeicaoAlimentoRepository) {

        this.planoRepository = planoRepository;
        this.usuarioRepository = usuarioRepository;
        this.refeicaoRepository = refeicaoRepository;
        this.refeicaoAlimentoRepository = refeicaoAlimentoRepository;
    }

    public PlanoAlimentar cadastrar(
            Long idUsuario, BigDecimal orcamento, String objetivo) {

        validarDados(orcamento, objetivo);

        Usuario usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Usuário não encontrado"));

        PlanoAlimentar plano = new PlanoAlimentar();
        plano.setUsuario(usuario);
        plano.setOrcamento(orcamento);
        plano.setObjetivo(objetivo);

        return planoRepository.save(plano);
    }

    public List<PlanoAlimentar> listarPorUsuario(Long idUsuario) {

        if (!usuarioRepository.existsById(idUsuario)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Usuário não encontrado");
        }

        return planoRepository
                .findByUsuarioIdUsuarioOrderByDataCriacaoDesc(idUsuario);
    }

    @Transactional
    public PlanoAlimentar atualizar(
            Long idPlano, BigDecimal orcamento, String objetivo) {

        validarDados(orcamento, objetivo);

        PlanoAlimentar plano = planoRepository.findById(idPlano)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Plano alimentar não encontrado"));

        plano.setOrcamento(orcamento);
        plano.setObjetivo(objetivo);

        return planoRepository.save(plano);
    }

    @Transactional
    public void excluir(Long idPlano) {

        PlanoAlimentar plano = planoRepository.findById(idPlano)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Plano alimentar não encontrado"));

        // Busca todas as refeições pertencentes ao plano.
        List<Refeicao> refeicoes =
                refeicaoRepository.findByPlanoAlimentarIdPlano(idPlano);

        // Exclui os vínculos entre cada refeição e seus alimentos.
        for (Refeicao refeicao : refeicoes) {
            refeicaoAlimentoRepository
                    .deleteByRefeicaoIdRefeicao(refeicao.getIdRefeicao());
        }

        // Exclui as refeições do plano.
        refeicaoRepository.deleteAll(refeicoes);

        // Por último, exclui o plano alimentar.
        planoRepository.delete(plano);
    }

    private void validarDados(BigDecimal orcamento, String objetivo) {

        if (orcamento == null || orcamento.compareTo(BigDecimal.ZERO) <= 0) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "O orçamento deve ser maior que zero");
        }

        if (objetivo == null || objetivo.isBlank()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Informe o objetivo do plano");
        }
    }
}
