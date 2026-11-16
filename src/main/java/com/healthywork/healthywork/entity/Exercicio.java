
package com.healthywork.healthywork.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "exercicio")
public class Exercicio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_exercicio")
    private Long idExercicio;

    @ManyToOne
    @JoinColumn(name = "id_categoria", nullable = false)
    private CategoriaExercicio categoria;

    @Column(nullable = false, length = 100)
    private String nome;

    @Column(columnDefinition = "TEXT")
    private String descricao;

    @Column(name = "duracao_minutos")
    private Integer duracaoMinutos;

    @Column(name = "calorias_estimadas")
    private Integer caloriasEstimadas;

    @Column(length = 30)
    private String local;

    public Long getIdExercicio() {
        return idExercicio;
    }

    public void setIdExercicio(Long idExercicio) {
        this.idExercicio = idExercicio;
    }

    public CategoriaExercicio getCategoria() {
        return categoria;
    }

    public void setCategoria(CategoriaExercicio categoria) {
        this.categoria = categoria;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Integer getDuracaoMinutos() {
        return duracaoMinutos;
    }

    public void setDuracaoMinutos(Integer duracaoMinutos) {
        this.duracaoMinutos = duracaoMinutos;
    }

    public Integer getCaloriasEstimadas() {
        return caloriasEstimadas;
    }

    public void setCaloriasEstimadas(Integer caloriasEstimadas) {
        this.caloriasEstimadas = caloriasEstimadas;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }
}
