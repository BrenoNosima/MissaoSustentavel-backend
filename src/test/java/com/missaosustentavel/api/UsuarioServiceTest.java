package com.missaosustentavel.api;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.missaosustentavel.api.model.Usuario;
import com.missaosustentavel.api.service.UsuarioService;

@SpringBootTest
public class UsuarioServiceTest {

    @Autowired
    private UsuarioService usuarioService;

    @Test
    public void createUsuario_shouldSave() {
        String unique = UUID.randomUUID().toString();
        Usuario u = new Usuario();
        u.setNome("Teste " + unique);
        u.setEmail("test-" + unique + "@example.com");
        u.setSenha("senha");

        Usuario saved = usuarioService.createUsuario(u);
        assertNotNull(saved.getId());
    }
}
