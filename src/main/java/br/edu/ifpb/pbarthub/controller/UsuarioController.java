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
    public String validarUsuario(@RequestParam("email_validar") String email_validar,
            @RequestParam("senha_validar") String senha_validar, Model model, RedirectAttributes attr) {

        Optional<Usuario> usuarioOpt = usuarioService.findByEmail(email_validar);

        if (usuarioOpt.isPresent()) {
            Usuario usuario = usuarioOpt.get();

            if (senha_validar.equals(usuario.getSenha())) {
                model.addAttribute("usuario", usuario);
                System.out.println(usuario.getNome());
                attr.addFlashAttribute("success", "Bem-vindo à Vitrine de Produtos da ArtHub, " + usuario.getNome());
                return produtoController.listarProdutosExibicao(model, usuario, attr);
            } else {
                // Senha incorreta
                model.addAttribute("erro", "Dados incorretos. Tente novamente.");
                return "acesso";
            }
        } else {
            // Email sem cadastro
            model.addAttribute("erro", "Não foi encontrado um cadastro com esse email. Tente novamente.");
            return "acesso";
        }
    }

@PostMapping("/carrinho")
public String adicionarCarrinho(@RequestParam("produtoId") Long produtoId, Usuario usuario, RedirectAttributes attr, Model model) {
    if (usuario == null) {
        attr.addFlashAttribute("erro", "Você precisa estar logado para adicionar itens ao carrinho.");
        return "redirect:/produtos/acesso";
    }

    Optional<Produto> produtoOpt = produtoService.buscarPorId(produtoId);
    if (produtoOpt.isPresent()) {
        Produto produto = produtoOpt.get();
        
        usuario.getCarrinho().add(produto);
        System.out.println(usuario.getCarrinho());
        attr.addFlashAttribute("success", produto.getNome() + " foi adicionado ao seu carrinho!");

    } else {
        attr.addFlashAttribute("erro", "Produto não encontrado.");
    }
    model.addAttribute("usuario", usuario);
    return exibirCarrinho(model, attr, usuario);
}

    @PostMapping("/exibirCarrinho")
    public String exibirCarrinho(Model model, RedirectAttributes attr, Usuario usuarioAtual) {

        if (usuarioAtual != null) {
            List<Produto> produtos = produtoService.listarTodos();

            if (produtos.size() > 3) {
                produtos = produtos.subList(0, 3); // Limita a lista aos 3 primeiros itens
            }

            model.addAttribute("usuario", usuarioAtual);
            model.addAttribute("produtos", produtos);
            model.addAttribute("produtosCarrinho", usuarioAtual.getCarrinho());
            System.out.println("Exibindo carrinho do usuário: " + usuarioAtual.getNome());
            return "usuario/carrinho";
        }

        attr.addFlashAttribute("erro", "Você precisa estar logado para ver o carrinho.");
        //System.out.println("Procurando pelo usuario: " + usuarioAtual);
        //System.out.println("Passou aqui?");
        return "redirect:/produtos/acesso";
    }

    @GetMapping("/confirmacaoCompra")
    public String confirmacaoCompra (Model model) {
        return "produto/confirmacaoCompra";
    }

}
