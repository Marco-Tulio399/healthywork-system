
package com.healthywork.healthywork.entity;

import jakarta.persistence.*;
import java.time.LocalTime;

@Entity
@Table(name = "refeicao")
public class Refeicao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_refeicao")
    private Long idRefeicao;

    @ManyToOne
    @JoinColumn(name = "id_plano", nullable = false)
    private PlanoAlimentar planoAlimentar;

    @Column(nullable = false, length = 30)
    private String tipo;

    @Column(nullable = false)
    private LocalTime horario;

    public Long getIdRefeicao() {
        return idRefeicao;
    }

    public void setIdRefeicao(Long idRefeicao) {
        this.idRefeicao = idRefeicao;
    }

    public PlanoAlimentar getPlanoAlimentar() {
        return planoAlimentar;
    }

    public void setPlanoAlimentar(PlanoAlimentar planoAlimentar) {
        this.planoAlimentar = planoAlimentar;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public LocalTime getHorario() {
        return horario;
    }

    public void setHorario(LocalTime horario) {
        this.horario = horario;
    }
}
