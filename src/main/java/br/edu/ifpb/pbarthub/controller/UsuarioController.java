package br.edu.ifpb.pbarthub.controller;

import br.edu.ifpb.pbarthub.model.Produto;
import br.edu.ifpb.pbarthub.model.Usuario;
import br.edu.ifpb.pbarthub.service.ProdutoService;
import br.edu.ifpb.pbarthub.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private ProdutoController produtoController;

    @Autowired
    private ProdutoService produtoService;

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

    @PostMapping("/vitrine/validar")
    public String validarUsuario(@RequestParam("email_validar") String email_validar,@RequestParam("senha_validar") String senha_validar, Model model,  RedirectAttributes attr) {

        // Checando se tem usuario com esse email
        Optional<Usuario> usuarioOpt = usuarioService.findByEmail(email_validar);

        // Usuario localizado
        if (usuarioOpt.isPresent()) {
            Usuario usuario = usuarioOpt.get();

            if (senha_validar.equals(usuario.getSenha())) {
                // Adiciona mensagem de sucesso e redireciona para a página de listagem de produtos
                attr.addFlashAttribute("success", "Bem-vindo à Vitrine de Produtos da ArtHub, " + usuario.getNome());
                return "redirect:/produtos/vitrine";
            } else {
                // Senha incorreta
                model.addAttribute("erro", "Dados incorretos. Tente novamente.");
                return "produto/acesso";
            }
        } else {
            // Email sem cadastro
            model.addAttribute("erro", "Não foi encontrado um cadastro com esse email. Tente novamente.");
            return "produto/acesso";
        }
    }

    @PostMapping("/carrinho")
    public void adicionarCarrinho(@RequestParam("produtoCarrinho") Produto produto, @RequestParam("usuarioCarrinho") Usuario usuario) {
        List<Produto> carrinho = usuario.getCarrinho();
        carrinho.add(produto);
    }
/*
    @PostMapping("/exibirCarrinho")
    public String exibirCarrinho(@RequestParam("usuarioCarrinhoEmail") String usuarioEmail,  RedirectAttributes attr, Model model) {
        //attr.addFlashAttribute("success", "Bem-vindo ao seu carrinho, " + usuarioId);
        //return "usuario/carrinho";

        // Busca o usuário pelo email
        Usuario usuarioCarrinho = usuarioService.findByEmail(usuarioEmail).orElse(null);

        // Verifica se o usuário existe
        if (usuarioCarrinho != null) {
            return getListaProdutosCarrinho(usuarioCarrinho, attr, model);
        } else {
            // Se o usuário não for encontrado, adicionar mensagem de erro
            attr.addFlashAttribute("erro", "Usuário não encontrado.");
            return "redirect:/erro";
        }
    }

    @GetMapping("/listarProdutosCarrinho")
    public String getListaProdutosCarrinho(Usuario usuarioCarrinho, RedirectAttributes attr, Model model) {
        List<Produto> produtosCarrinho = usuarioCarrinho.getCarrinho();

        List<Produto> produtos = produtoService.listarTodos();


        List<Produto> produtosCarrinhoUsuario = produtosCarrinho.stream()
                .filter(produtoCarrinho -> produtoCarrinho.getDescricao().equals())
                .toList();


        model.addAttribute("produtos", produtosCarrinhoUsuario);


        return "usuario/carrinho";
    }

 */



}

