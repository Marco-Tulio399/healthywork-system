
package com.healthywork.healthywork.repository;

import com.healthywork.healthywork.entity.RefeicaoAlimento;
import com.healthywork.healthywork.entity.RefeicaoAlimentoId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RefeicaoAlimentoRepository
        extends JpaRepository<RefeicaoAlimento, RefeicaoAlimentoId> {

    List<RefeicaoAlimento> findByRefeicaoIdRefeicao(Long idRefeicao);

    Optional<RefeicaoAlimento>
    findByRefeicaoIdRefeicaoAndAlimentoIdAlimento(
            Long idRefeicao,
            Long idAlimento
    );

    void deleteByRefeicaoIdRefeicao(Long idRefeicao);
}
