
package com.healthywork.healthywork.repository;

import com.healthywork.healthywork.entity.Exercicio;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExercicioRepository extends JpaRepository<Exercicio, Long> {

    List<Exercicio> findByCategoriaIdCategoria(Long idCategoria);
}
