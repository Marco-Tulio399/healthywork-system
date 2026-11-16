
package com.healthywork.healthywork.service;

import com.healthywork.healthywork.entity.Usuario;
import com.healthywork.healthywork.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioService(
            UsuarioRepository usuarioRepository,
            PasswordEncoder passwordEncoder) {

        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public boolean emailExiste(String email) {
        return usuarioRepository.findByEmail(email).isPresent();
    }

    public Usuario cadastrarUsuario(Usuario usuario) {

        if (emailExiste(usuario.getEmail())) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Este e-mail já está cadastrado."
            );
        }

        usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));

        return usuarioRepository.save(usuario);
    }

    public Usuario autenticarUsuario(String email, String senha) {

        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.UNAUTHORIZED,
                        "E-mail ou senha incorretos."
                ));

        if (!passwordEncoder.matches(senha, usuario.getSenha())) {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED,
                    "E-mail ou senha incorretos."
            );
        }

        return usuario;
    }
}
