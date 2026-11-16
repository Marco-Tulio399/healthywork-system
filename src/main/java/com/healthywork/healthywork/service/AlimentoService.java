
package com.healthywork.healthywork.service;

import com.healthywork.healthywork.entity.Alimento;
import com.healthywork.healthywork.repository.AlimentoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AlimentoService {

    private final AlimentoRepository alimentoRepository;

    public AlimentoService(AlimentoRepository alimentoRepository) {
        this.alimentoRepository = alimentoRepository;
    }

    public Alimento cadastrar(Alimento alimento) {
        return alimentoRepository.save(alimento);
    }

    public List<Alimento> listarTodos() {
        return alimentoRepository.findAll();
    }

    public Alimento buscarPorId(Long id) {
        return alimentoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(
                        "Alimento não encontrado"));
    }

    public void excluir(Long id) {
        Alimento alimento = buscarPorId(id);
        alimentoRepository.delete(alimento);
    }
}
