package br.edu.ifpb.pbarthub.controller;

import br.edu.ifpb.pbarthub.model.Produto;
import br.edu.ifpb.pbarthub.model.Usuario;
import br.edu.ifpb.pbarthub.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;
// import java.util.jar.Attributes;

@Controller
@RequestMapping("/produtos")
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;


    @GetMapping
    public String listarProdutos(Model model) {
        model.addAttribute("produtos", produtoService.listarTodos());
        return "produto/listar";
    }

    @GetMapping("/vitrine")
    public String listarProdutosExibicao(Model model, Usuario usuario, RedirectAttributes attr) {
        List<Produto> produtos = produtoService.listarTodos();

        model.addAttribute("usuario", usuario);
        System.out.println("Na página montada: " + usuario.getNome());
        model.addAttribute("produtos", produtos);
        return "produto/listarExibicao";
    }

    @GetMapping("/novo")
    public String novoProduto(Model model) {
        model.addAttribute("produto", new Produto());
        return "produto/formulario";
    }

    @PostMapping
    public String salvarProduto(@ModelAttribute Produto produto) {
        produtoService.salvar(produto);
        return "redirect:/produtos";
    }

    @GetMapping("/editar/{id}")
    public String editarProduto(@PathVariable Long id, Model model) {
        Optional<Produto> produto = produtoService.buscarPorId(id);
        if (produto.isPresent()) {
            model.addAttribute("produto", produto.get());
            return "produto/formulario";
        }
        return "redirect:/produtos";
    }

    @GetMapping("/excluir/{id}")
    public String excluirProduto(@PathVariable Long id) {
        produtoService.excluir(id);
        return "redirect:/produtos";
    }

    @GetMapping("/acesso")
    public String acessoVitrine() {
        return "produto/acesso";
    }


}
