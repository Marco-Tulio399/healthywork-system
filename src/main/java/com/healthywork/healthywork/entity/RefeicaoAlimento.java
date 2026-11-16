
package com.healthywork.healthywork.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "refeicao_alimento")
@IdClass(RefeicaoAlimentoId.class)
public class RefeicaoAlimento {

    @Id
    @ManyToOne
    @JoinColumn(name = "id_refeicao", nullable = false)
    private Refeicao refeicao;

    @Id
    @ManyToOne
    @JoinColumn(name = "id_alimento", nullable = false)
    private Alimento alimento;

    @Column(name = "quantidade", nullable = false)
    private BigDecimal quantidade;

    public Refeicao getRefeicao() {
        return refeicao;
    }

    public void setRefeicao(Refeicao refeicao) {
        this.refeicao = refeicao;
    }

    public Alimento getAlimento() {
        return alimento;
    }

    public void setAlimento(Alimento alimento) {
        this.alimento = alimento;
    }

    public BigDecimal getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(BigDecimal quantidade) {
        this.quantidade = quantidade;
    }
}
