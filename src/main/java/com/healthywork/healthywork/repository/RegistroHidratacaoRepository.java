
package com.healthywork.healthywork.repository;

import com.healthywork.healthywork.entity.RegistroHidratacao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface RegistroHidratacaoRepository
        extends JpaRepository<RegistroHidratacao, Long> {

    Optional<RegistroHidratacao> findByUsuarioIdUsuarioAndDataRegistro(
            Long idUsuario, LocalDate dataRegistro);

}
