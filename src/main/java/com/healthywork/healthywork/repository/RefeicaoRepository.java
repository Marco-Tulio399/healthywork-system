
package com.healthywork.healthywork.repository;

import com.healthywork.healthywork.entity.Refeicao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RefeicaoRepository extends JpaRepository<Refeicao, Long> {

    List<Refeicao> findByPlanoAlimentarIdPlano(Long idPlano);

}
