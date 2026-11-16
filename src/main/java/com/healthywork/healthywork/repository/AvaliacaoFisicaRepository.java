
package com.healthywork.healthywork.repository;

import com.healthywork.healthywork.entity.AvaliacaoFisica;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AvaliacaoFisicaRepository extends JpaRepository<AvaliacaoFisica, Long> {

    List<AvaliacaoFisica> findByUsuarioIdUsuarioOrderByDataAvaliacaoDesc(Long idUsuario);

}
