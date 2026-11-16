
package com.healthywork.healthywork.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "avaliacao_fisica")
public class AvaliacaoFisica {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_avaliacao")
    private Long idAvaliacao;

    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    @Column(nullable = false)
    private BigDecimal peso;

    @Column(nullable = false)
    private BigDecimal altura;

    @Column(nullable = false)
    private BigDecimal imc;

    @Column(length = 30)
    private String classificacao;

    @Column(name = "data_avaliacao", nullable = false)
    private LocalDateTime dataAvaliacao;

    public AvaliacaoFisica() {}

    @PrePersist
    public void prePersist() {
        if (dataAvaliacao == null) {
            dataAvaliacao = LocalDateTime.now();
        }
    }

    public Long getIdAvaliacao() {
        return idAvaliacao;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public BigDecimal getPeso() {
        return peso;
    }

    public void setPeso(BigDecimal peso) {
        this.peso = peso;
    }

    public BigDecimal getAltura() {
        return altura;
    }

    public void setAltura(BigDecimal altura) {
        this.altura = altura;
    }

    public BigDecimal getImc() {
        return imc;
    }

    public void setImc(BigDecimal imc) {
        this.imc = imc;
    }

    public String getClassificacao() {
        return classificacao;
    }

    public void setClassificacao(String classificacao) {
        this.classificacao = classificacao;
    }

    public LocalDateTime getDataAvaliacao() {
        return dataAvaliacao;
    }
}
