
package com.healthywork.healthywork.service;

import com.healthywork.healthywork.entity.AvaliacaoFisica;
import com.healthywork.healthywork.entity.RegistroHidratacao;
import com.healthywork.healthywork.entity.Usuario;
import com.healthywork.healthywork.repository.AvaliacaoFisicaRepository;
import com.healthywork.healthywork.repository.RegistroHidratacaoRepository;
import com.healthywork.healthywork.repository.UsuarioRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;

@Service
public class RegistroHidratacaoService {

    private final RegistroHidratacaoRepository registroRepository;
    private final UsuarioRepository usuarioRepository;
    private final AvaliacaoFisicaRepository avaliacaoRepository;

    public RegistroHidratacaoService(
            RegistroHidratacaoRepository registroRepository,
            UsuarioRepository usuarioRepository,
            AvaliacaoFisicaRepository avaliacaoRepository) {
        this.registroRepository = registroRepository;
        this.usuarioRepository = usuarioRepository;
        this.avaliacaoRepository = avaliacaoRepository;
    }

    public RegistroHidratacao obterOuCriarRegistro(Long idUsuario) {

        Usuario usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Usuário não encontrado"));

        LocalDate hoje = LocalDate.now();

        List<AvaliacaoFisica> avaliacoes =
                avaliacaoRepository
                        .findByUsuarioIdUsuarioOrderByDataAvaliacaoDesc(idUsuario);

        int metaAgua = 2000;

        if (!avaliacoes.isEmpty()) {
            double peso = avaliacoes.get(0).getPeso().doubleValue();
            metaAgua = (int) Math.round(peso * 35);
        }

        RegistroHidratacao registro = registroRepository
                .findByUsuarioIdUsuarioAndDataRegistro(idUsuario, hoje)
                .orElseGet(() -> {
                    RegistroHidratacao novo = new RegistroHidratacao();
                    novo.setUsuario(usuario);
                    novo.setConsumidoMl(0);
                    return novo;
                });

        registro.setMetaMl(metaAgua);

        return registroRepository.save(registro);
    }

    public RegistroHidratacao adicionarAgua(Long idUsuario, Integer quantidadeMl) {

        if (quantidadeMl == null || quantidadeMl <= 0) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Quantidade de água inválida");
        }

        RegistroHidratacao registro = obterOuCriarRegistro(idUsuario);

        registro.setConsumidoMl(
                registro.getConsumidoMl() + quantidadeMl);

        return registroRepository.save(registro);
    }
}
