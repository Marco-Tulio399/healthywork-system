
package com.healthywork.healthywork.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "plano_exercicio")
public class PlanoExercicio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_plano_exercicio")
    private Long idPlanoExercicio;

    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "id_exercicio", nullable = false)
    private Exercicio exercicio;

    @Column(nullable = false)
    private LocalDate data;

    @Column(nullable = false)
    private Boolean concluido = false;

    @PrePersist
    public void prePersist() {
        if (data == null) {
            data = LocalDate.now();
        }
        if (concluido == null) {
            concluido = false;
        }
    }

    public Long getIdPlanoExercicio() {
        return idPlanoExercicio;
    }

    public void setIdPlanoExercicio(Long idPlanoExercicio) {
        this.idPlanoExercicio = idPlanoExercicio;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Exercicio getExercicio() {
        return exercicio;
    }

    public void setExercicio(Exercicio exercicio) {
        this.exercicio = exercicio;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public Boolean getConcluido() {
        return concluido;
    }

    public void setConcluido(Boolean concluido) {
        this.concluido = concluido;
    }
}
