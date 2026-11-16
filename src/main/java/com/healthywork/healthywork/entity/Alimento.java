
package com.healthywork.healthywork.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "alimento")
public class Alimento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_alimento")
    private Long idAlimento;

    @Column(nullable = false, length = 100)
    private String nome;

    private BigDecimal calorias;

    private BigDecimal proteinas;

    private BigDecimal carboidratos;

    private BigDecimal gorduras;

    @Column(name = "preco_medio")
    private BigDecimal precoMedio;

    public Long getIdAlimento() {
        return idAlimento;
    }

    public void setIdAlimento(Long idAlimento) {
        this.idAlimento = idAlimento;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public BigDecimal getCalorias() {
        return calorias;
    }

    public void setCalorias(BigDecimal calorias) {
        this.calorias = calorias;
    }

    public BigDecimal getProteinas() {
        return proteinas;
    }

    public void setProteinas(BigDecimal proteinas) {
        this.proteinas = proteinas;
    }

    public BigDecimal getCarboidratos() {
        return carboidratos;
    }

    public void setCarboidratos(BigDecimal carboidratos) {
        this.carboidratos = carboidratos;
    }

    public BigDecimal getGorduras() {
        return gorduras;
    }

    public void setGorduras(BigDecimal gorduras) {
        this.gorduras = gorduras;
    }

    public BigDecimal getPrecoMedio() {
        return precoMedio;
    }

    public void setPrecoMedio(BigDecimal precoMedio) {
        this.precoMedio = precoMedio;
    }
}
