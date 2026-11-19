
package com.healthywork.healthywork;

import com.healthywork.healthywork.dto.CadastroUsuarioRequest;
import com.healthywork.healthywork.dto.LoginRequest;
import com.healthywork.healthywork.dto.UsuarioResponse;
import com.healthywork.healthywork.entity.Usuario;
import com.healthywork.healthywork.service.UsuarioService;
import org.springframework.web.bind.annotation.CrossOrigin;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping("/cadastro")
    public ResponseEntity<UsuarioResponse> cadastrar(
            @Valid @RequestBody CadastroUsuarioRequest dados) {

        Usuario usuario = new Usuario();

        usuario.setNome(dados.nome());
        usuario.setEmail(dados.email());
        usuario.setSenha(dados.senha());
        usuario.setDataNascimento(dados.dataNascimento());
        usuario.setSexo(dados.sexo());

        Usuario salvo = usuarioService.cadastrarUsuario(usuario);

        UsuarioResponse resposta = new UsuarioResponse(
                salvo.getIdUsuario(),
                salvo.getNome(),
                salvo.getEmail()
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(resposta);
    }

    @PostMapping("/login")
    public ResponseEntity<UsuarioResponse> login(
            @Valid @RequestBody LoginRequest dados) {

        Usuario usuario = usuarioService.autenticarUsuario(
                dados.email(),
                dados.senha()
        );

        UsuarioResponse resposta = new UsuarioResponse(
                usuario.getIdUsuario(),
                usuario.getNome(),
                usuario.getEmail()
        );

        return ResponseEntity.ok(resposta);
    }
}
