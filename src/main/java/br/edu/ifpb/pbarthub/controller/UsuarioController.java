package br.edu.ifpb.pbarthub.controller;

import br.edu.ifpb.pbarthub.model.Usuario;
import br.edu.ifpb.pbarthub.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import br.edu.ifpb.pbarthub.controller.ProdutoController;


import java.util.Optional;

@Controller
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    public String listarUsuarios(Model model) {
        model.addAttribute("usuarios", usuarioService.listarTodos());
        return "usuario/listar";
    }

    @GetMapping("/novo")
    public String novoUsuario(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "usuario/formulario";
    }

    @PostMapping
    public String salvarUsuario(@ModelAttribute Usuario usuario) {
        usuarioService.salvar(usuario);
        return "redirect:/usuarios";
    }

    @GetMapping("/editar/{id}")
    public String editarUsuario(@PathVariable Long id, Model model) {
        Optional<Usuario> usuario = usuarioService.buscarPorId(id);
        if (usuario.isPresent()) {
            model.addAttribute("usuario", usuario.get());
            return "usuario/formulario";
        }
        return "redirect:/usuarios";
    }

    @GetMapping("/excluir/{id}")
    public String excluirUsuario(@PathVariable Long id) {
        usuarioService.excluir(id);
        return "redirect:/usuarios";
    }

    private String email_teste = "am@gmail.com";
    @PostMapping("/vitrine/validar")
    public String validarUsuario(@RequestParam("email_validar") String email_validar,@RequestParam("senha_validar") String senha_validar, Model model) {

        if (email_teste.equals(email_validar)) {
            return "redirect:/produtos/vitrine";
        } else {
            model.addAttribute("erro", "Usuario não encontrado. Tente novamente.");
            return "produto/acesso";
        }
    }
}

