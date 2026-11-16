
package com.healthywork.healthywork.repository;

import com.healthywork.healthywork.entity.PlanoExercicio;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlanoExercicioRepository
        extends JpaRepository<PlanoExercicio, Long> {

    List<PlanoExercicio> findByUsuarioIdUsuarioOrderByDataDesc(Long idUsuario);
}
