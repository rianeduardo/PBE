package dev.rianeduardo.estoque.controllers;

import jakarta.servlet.http.HttpSession;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import dev.rianeduardo.estoque.models.*;
import dev.rianeduardo.estoque.repositories.*;

@Controller
@RequestMapping("/app/materiais")
public class MateriaisController {

    @Autowired
    private MaterialRepository materialRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @GetMapping
    public String listarMateriais(HttpSession session, Model model) {
        if (session.getAttribute("usuarioLogado") == null)
            return "redirect:/login";

        model.addAttribute("materiais", materialRepository.findAll());
        return "app/materiais/listar";
    }

    @GetMapping("/novo")
    public String novoMaterial(HttpSession session, Model model) {
        if (session.getAttribute("usuarioLogado") == null)
            return "redirect:/login";

        Materiais materialVazio = new Materiais();

        model.addAttribute("material", materialVazio);

        model.addAttribute("categorias", categoriaRepository.findAll());
        return "app/materiais/form";
    }

    @PostMapping("/salvar")
    public String salvarMaterial(@ModelAttribute Materiais material, HttpSession session) {
        if (session.getAttribute("usuarioLogado") == null)
            return "redirect:/login";

        materialRepository.save(material);
        return "redirect:/app/materiais";
    }

    @GetMapping("/editar/{id}")
    public String editarMaterial(@PathVariable("id") Long id, Model model, HttpSession session) {
        if (session.getAttribute("usuarioLogado") == null)
            return "redirect:/login";

        Materiais material = materialRepository.findById(id).orElse(null);
        if (material == null)
            return "redirect:/app/materiais";

        model.addAttribute("material", material);
        model.addAttribute("categorias", categoriaRepository.findAll());

        return "app/materiais/editar";
    }

    @GetMapping("/excluir/{id}")
    public String excluirMaterial(@PathVariable("id") Long id, HttpSession session,
            org.springframework.web.servlet.mvc.support.RedirectAttributes redirectAttributes) {
        if (session.getAttribute("usuarioLogado") == null)
            return "redirect:/login";

        try {
            materialRepository.deleteById(id);
            redirectAttributes.addFlashAttribute("mensagem", "Material excluído com sucesso!");
        } catch (org.springframework.dao.DataIntegrityViolationException e) {
            redirectAttributes.addFlashAttribute("erro",
                    "Não é possível excluir este material porque ele possui movimentações ou ativos vinculados a ele.");
        }

        return "redirect:/app/materiais";
    }

    @GetMapping("/alertas")
    public String verAlertasEstoque(HttpSession session, Model model) {
        if (session.getAttribute("usuarioLogado") == null)
            return "redirect:/login";

        List<Materiais> materiaisEmAlerta = materialRepository.findByEstoqueLessThanEqual(5);

        model.addAttribute("alertas", materiaisEmAlerta);
        return "app/materiais/alertas";
    }
}