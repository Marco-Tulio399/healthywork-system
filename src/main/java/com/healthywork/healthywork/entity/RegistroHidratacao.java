
package com.healthywork.healthywork.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "registro_hidratacao")
public class RegistroHidratacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_registro")
    private Long idRegistro;

    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    @Column(name = "meta_ml", nullable = false)
    private Integer metaMl;

    @Column(name = "consumido_ml", nullable = false)
    private Integer consumidoMl = 0;

    @Column(name = "data_registro", nullable = false)
    private LocalDate dataRegistro;

    public RegistroHidratacao() {}

    @PrePersist
    public void prePersist() {
        if (dataRegistro == null) {
            dataRegistro = LocalDate.now();
        }
        if (consumidoMl == null) {
            consumidoMl = 0;
        }
    }

    public Long getIdRegistro() {
        return idRegistro;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Integer getMetaMl() {
        return metaMl;
    }

    public void setMetaMl(Integer metaMl) {
        this.metaMl = metaMl;
    }

    public Integer getConsumidoMl() {
        return consumidoMl;
    }

    public void setConsumidoMl(Integer consumidoMl) {
        this.consumidoMl = consumidoMl;
    }

    public LocalDate getDataRegistro() {
        return dataRegistro;
    }
}
