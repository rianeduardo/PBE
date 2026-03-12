package dev.rianeduardo.estoque.controllers;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import dev.rianeduardo.estoque.models.Categorias;
import dev.rianeduardo.estoque.repositories.CategoriaRepository;

@Controller
@RequestMapping("/app/categorias")
public class CategoriaController {

    @Autowired
    private CategoriaRepository categoriaRepository;

    @GetMapping
    public String listarCategorias(HttpSession session, Model model) {
        if (session.getAttribute("usuarioLogado") == null)
            return "redirect:/login";
        model.addAttribute("categorias", categoriaRepository.findAll());
        return "app/categorias/listar";
    }

    @GetMapping("/nova")
    public String novaCategoria(HttpSession session, Model model) {
        if (session.getAttribute("usuarioLogado") == null)
            return "redirect:/login";
        model.addAttribute("categoria", new Categorias());
        return "app/categorias/form";
    }

    @PostMapping("/salvar")
    public String salvarCategoria(@ModelAttribute Categorias categoria, HttpSession session) {
        if (session.getAttribute("usuarioLogado") == null)
            return "redirect:/login";
        categoriaRepository.save(categoria);
        return "redirect:/app/categorias";
    }

    @GetMapping("/editar/{id}")
    public String editarCategoria(@PathVariable("id") Long id, Model model, HttpSession session) {
        if (session.getAttribute("usuarioLogado") == null)
            return "redirect:/login";
        Categorias categoria = categoriaRepository.findById(id).orElse(null);
        if (categoria == null)
            return "redirect:/app/categorias";

        model.addAttribute("categoria", categoria);
        return "app/categorias/form";
    }

    @GetMapping("/excluir/{id}")
    public String excluirCategoria(@PathVariable("id") Long id, HttpSession session) {
        if (session.getAttribute("usuarioLogado") == null)
            return "redirect:/login";
        categoriaRepository.deleteById(id);
        return "redirect:/app/categorias";
    }
}