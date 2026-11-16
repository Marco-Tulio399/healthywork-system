
package com.healthywork.healthywork.entity;

import java.io.Serializable;
import java.util.Objects;

public class RefeicaoAlimentoId implements Serializable {

    private Long refeicao;
    private Long alimento;

    public RefeicaoAlimentoId() {
    }

    public RefeicaoAlimentoId(Long refeicao, Long alimento) {
        this.refeicao = refeicao;
        this.alimento = alimento;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RefeicaoAlimentoId that)) return false;
        return Objects.equals(refeicao, that.refeicao)
                && Objects.equals(alimento, that.alimento);
    }

    @Override
    public int hashCode() {
        return Objects.hash(refeicao, alimento);
    }
}
