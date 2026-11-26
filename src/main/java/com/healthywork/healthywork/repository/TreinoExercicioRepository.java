
package com.healthywork.healthywork.repository;

import com.healthywork.healthywork.entity.TreinoExercicio;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TreinoExercicioRepository extends JpaRepository<TreinoExercicio, Long> {

    List<TreinoExercicio> findByTreinoIdTreinoOrderByOrdemAsc(Long idTreino);

    void deleteByTreinoIdTreino(Long idTreino);

}
