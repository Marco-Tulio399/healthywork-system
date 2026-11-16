
package com.healthywork.healthywork.repository;

import com.healthywork.healthywork.entity.PlanoAlimentar;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlanoAlimentarRepository extends JpaRepository<PlanoAlimentar, Long> {

    List<PlanoAlimentar> findByUsuarioIdUsuarioOrderByDataCriacaoDesc(Long idUsuario);

}
