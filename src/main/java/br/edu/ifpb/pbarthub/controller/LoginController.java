package br.edu.ifpb.pbarthub.controller;

import br.edu.ifpb.pbarthub.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/validar")
    public String login(@RequestParam String email, @RequestParam String senha, Model model) {
        if (usuarioService.validarLogin(email, senha)) {
            return "redirect:http://localhost:9090/PbArtHub";
        } else {
            // Login falhou, mostrar erro
            model.addAttribute("erro", "Email ou senha inválidos");
            return "redirect:http://localhost:9090/PbArtHub/usuarios/login";
        }
    }
}
