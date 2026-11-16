
package com.healthywork.healthywork;

import com.healthywork.healthywork.entity.Alimento;
import com.healthywork.healthywork.service.AlimentoService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/alimentos")
@CrossOrigin(origins = "*")
public class AlimentoController {

    private final AlimentoService alimentoService;

    public AlimentoController(AlimentoService alimentoService) {
        this.alimentoService = alimentoService;
    }

    @PostMapping
    public Alimento cadastrar(@RequestBody Alimento alimento) {
        return alimentoService.cadastrar(alimento);
    }

    @GetMapping
    public List<Alimento> listarTodos() {
        return alimentoService.listarTodos();
    }

    @GetMapping("/{id}")
    public Alimento buscarPorId(@PathVariable Long id) {
        return alimentoService.buscarPorId(id);
    }

    @DeleteMapping("/{id}")
    public void excluir(@PathVariable Long id) {
        alimentoService.excluir(id);
    }
}
