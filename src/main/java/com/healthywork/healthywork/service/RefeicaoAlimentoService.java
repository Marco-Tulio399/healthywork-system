
package com.healthywork.healthywork.service;

import com.healthywork.healthywork.dto.RefeicaoAlimentoRequest;
import com.healthywork.healthywork.dto.RefeicaoAlimentoResponse;
import com.healthywork.healthywork.entity.Alimento;
import com.healthywork.healthywork.entity.Refeicao;
import com.healthywork.healthywork.entity.RefeicaoAlimento;
import com.healthywork.healthywork.repository.AlimentoRepository;
import com.healthywork.healthywork.repository.RefeicaoAlimentoRepository;
import com.healthywork.healthywork.repository.RefeicaoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
public class RefeicaoAlimentoService {

    private final RefeicaoAlimentoRepository repository;
    private final RefeicaoRepository refeicaoRepository;
    private final AlimentoRepository alimentoRepository;

    public RefeicaoAlimentoService(
            RefeicaoAlimentoRepository repository,
            RefeicaoRepository refeicaoRepository,
            AlimentoRepository alimentoRepository) {

        this.repository = repository;
        this.refeicaoRepository = refeicaoRepository;
        this.alimentoRepository = alimentoRepository;
    }

    public RefeicaoAlimentoResponse adicionar(
            Long idRefeicao,
            RefeicaoAlimentoRequest request) {

        validarQuantidade(request.quantidade());

        Refeicao refeicao = refeicaoRepository.findById(idRefeicao)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Refeição não encontrada"
                ));

        Alimento alimento = alimentoRepository.findById(request.idAlimento())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Alimento não encontrado"
                ));

        RefeicaoAlimento item = new RefeicaoAlimento();
        item.setRefeicao(refeicao);
        item.setAlimento(alimento);
        item.setQuantidade(request.quantidade());

        repository.save(item);

        return converter(item);
    }

    public List<RefeicaoAlimentoResponse> listar(Long idRefeicao) {

        if (!refeicaoRepository.existsById(idRefeicao)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Refeição não encontrada"
            );
        }

        return repository.findByRefeicaoIdRefeicao(idRefeicao)
                .stream()
                .map(this::converter)
                .toList();
    }

    public RefeicaoAlimentoResponse atualizar(
            Long idRefeicao,
            Long idAlimento,
            BigDecimal quantidade) {

        validarQuantidade(quantidade);

        RefeicaoAlimento item = repository
                .findByRefeicaoIdRefeicaoAndAlimentoIdAlimento(
                        idRefeicao,
                        idAlimento
                )
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Alimento não encontrado nesta refeição"
                ));

        item.setQuantidade(quantidade);

        repository.save(item);

        return converter(item);
    }

    public void excluir(Long idRefeicao, Long idAlimento) {

        RefeicaoAlimento item = repository
                .findByRefeicaoIdRefeicaoAndAlimentoIdAlimento(
                        idRefeicao,
                        idAlimento
                )
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Alimento não encontrado nesta refeição"
                ));

        repository.delete(item);
    }

    private void validarQuantidade(BigDecimal quantidade) {

        if (quantidade == null ||
                quantidade.compareTo(BigDecimal.ZERO) <= 0) {

            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "A quantidade deve ser maior que zero"
            );
        }
    }

    private RefeicaoAlimentoResponse converter(RefeicaoAlimento item) {

        BigDecimal calorias = BigDecimal.ZERO;

        if (item.getAlimento().getCalorias() != null) {
            calorias = item.getAlimento().getCalorias()
                    .multiply(item.getQuantidade())
                    .divide(
                            BigDecimal.valueOf(100),
                            2,
                            RoundingMode.HALF_UP
                    );
        }

        return new RefeicaoAlimentoResponse(
                item.getRefeicao().getIdRefeicao(),
                item.getAlimento().getIdAlimento(),
                item.getAlimento().getNome(),
                item.getQuantidade(),
                calorias
        );
    }
}
