
package com.healthywork.healthywork.repository;

import com.healthywork.healthywork.entity.RefeicaoAlimento;
import com.healthywork.healthywork.entity.RefeicaoAlimentoId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RefeicaoAlimentoRepository
        extends JpaRepository<RefeicaoAlimento, RefeicaoAlimentoId> {

    List<RefeicaoAlimento> findByRefeicaoIdRefeicao(Long idRefeicao);
}
