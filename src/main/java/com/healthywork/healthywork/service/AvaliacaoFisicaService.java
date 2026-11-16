
package com.healthywork.healthywork.service;

import com.healthywork.healthywork.entity.AvaliacaoFisica;
import com.healthywork.healthywork.entity.Usuario;
import com.healthywork.healthywork.repository.AvaliacaoFisicaRepository;
import com.healthywork.healthywork.repository.UsuarioRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
public class AvaliacaoFisicaService {

    private final AvaliacaoFisicaRepository avaliacaoRepository;
    private final UsuarioRepository usuarioRepository;

    public AvaliacaoFisicaService(
            AvaliacaoFisicaRepository avaliacaoRepository,
            UsuarioRepository usuarioRepository) {
        this.avaliacaoRepository = avaliacaoRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public AvaliacaoFisica cadastrarAvaliacao(
            Long idUsuario, BigDecimal peso, BigDecimal altura) {

        if (peso == null || peso.compareTo(BigDecimal.ZERO) <= 0) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Peso inválido.");
        }

        if (altura == null || altura.compareTo(BigDecimal.ZERO) <= 0) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Altura inválida.");
        }

        Usuario usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Usuário não encontrado."));

        BigDecimal alturaAoQuadrado = altura.multiply(altura);

        BigDecimal imc = peso.divide(
                alturaAoQuadrado, 2, RoundingMode.HALF_UP);

        AvaliacaoFisica avaliacao = new AvaliacaoFisica();
        avaliacao.setUsuario(usuario);
        avaliacao.setPeso(peso);
        avaliacao.setAltura(altura);
        avaliacao.setImc(imc);
        avaliacao.setClassificacao(classificarIMC(imc));

        return avaliacaoRepository.save(avaliacao);
    }

    public List<AvaliacaoFisica> listarAvaliacoes(Long idUsuario) {
        return avaliacaoRepository
                .findByUsuarioIdUsuarioOrderByDataAvaliacaoDesc(idUsuario);
    }

    private String classificarIMC(BigDecimal imc) {
        if (imc.compareTo(new BigDecimal("18.5")) < 0) {
            return "Abaixo do peso";
        } else if (imc.compareTo(new BigDecimal("25")) < 0) {
            return "Peso normal";
        } else if (imc.compareTo(new BigDecimal("30")) < 0) {
            return "Sobrepeso";
        } else if (imc.compareTo(new BigDecimal("35")) < 0) {
            return "Obesidade grau I";
        } else if (imc.compareTo(new BigDecimal("40")) < 0) {
            return "Obesidade grau II";
        } else {
            return "Obesidade grau III";
        }
    }
}
