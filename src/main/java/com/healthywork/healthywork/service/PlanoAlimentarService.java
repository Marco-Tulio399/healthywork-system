
package com.healthywork.healthywork.service;

import com.healthywork.healthywork.entity.PlanoAlimentar;
import com.healthywork.healthywork.entity.Usuario;
import com.healthywork.healthywork.repository.PlanoAlimentarRepository;
import com.healthywork.healthywork.repository.UsuarioRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.List;

@Service
public class PlanoAlimentarService {

    private final PlanoAlimentarRepository planoRepository;
    private final UsuarioRepository usuarioRepository;

    public PlanoAlimentarService(
            PlanoAlimentarRepository planoRepository,
            UsuarioRepository usuarioRepository) {
        this.planoRepository = planoRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public PlanoAlimentar cadastrar(
            Long idUsuario, BigDecimal orcamento, String objetivo) {

        if (orcamento == null || orcamento.compareTo(BigDecimal.ZERO) <= 0) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "O orçamento deve ser maior que zero");
        }

        if (objetivo == null || objetivo.isBlank()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Informe o objetivo do plano");
        }

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
}
