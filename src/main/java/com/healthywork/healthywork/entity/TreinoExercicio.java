
package com.healthywork.healthywork.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "treino_exercicio")
public class TreinoExercicio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_treino_exercicio")
    private Long idTreinoExercicio;

    @ManyToOne
    @JoinColumn(name = "id_treino", nullable = false)
    private Treino treino;

    @ManyToOne
    @JoinColumn(name = "id_exercicio", nullable = false)
    private Exercicio exercicio;

    @Column(nullable = false)
    private Integer ordem;

    @Column(nullable = false)
    private Integer series;

    @Column(nullable = false)
    private Integer repeticoes;

    @Column(name = "descanso_segundos")
    private Integer descansoSegundos;

    public Long getIdTreinoExercicio() {
        return idTreinoExercicio;
    }

    public void setIdTreinoExercicio(Long idTreinoExercicio) {
        this.idTreinoExercicio = idTreinoExercicio;
    }

    public Treino getTreino() {
        return treino;
    }

    public void setTreino(Treino treino) {
        this.treino = treino;
    }

    public Exercicio getExercicio() {
        return exercicio;
    }

    public void setExercicio(Exercicio exercicio) {
        this.exercicio = exercicio;
    }

    public Integer getOrdem() {
        return ordem;
    }

    public void setOrdem(Integer ordem) {
        this.ordem = ordem;
    }

    public Integer getSeries() {
        return series;
    }

    public void setSeries(Integer series) {
        this.series = series;
    }

    public Integer getRepeticoes() {
        return repeticoes;
    }

    public void setRepeticoes(Integer repeticoes) {
        this.repeticoes = repeticoes;
    }

    public Integer getDescansoSegundos() {
        return descansoSegundos;
    }

    public void setDescansoSegundos(Integer descansoSegundos) {
        this.descansoSegundos = descansoSegundos;
    }
}
