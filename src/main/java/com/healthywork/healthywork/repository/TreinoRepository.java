
package com.healthywork.healthywork.repository;

import com.healthywork.healthywork.entity.Treino;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TreinoRepository extends JpaRepository<Treino, Long> {

    List<Treino> findByUsuarioIdUsuarioOrderByDataCriacaoDesc(Long idUsuario);

}
