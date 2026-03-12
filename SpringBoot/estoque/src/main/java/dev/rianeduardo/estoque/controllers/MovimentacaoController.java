package dev.rianeduardo.estoque.controllers;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import dev.rianeduardo.estoque.models.*;
import dev.rianeduardo.estoque.repositories.MovimentacaoRepository;
import dev.rianeduardo.estoque.repositories.MaterialRepository;

@Controller
@RequestMapping("/app/movimentacoes")
public class MovimentacaoController {

    @Autowired
    private MovimentacaoRepository movimentacaoRepository;

    @Autowired
    private MaterialRepository materialRepository;

    @GetMapping
    public String listarMovimentacoes(HttpSession session, Model model) {
        if (session.getAttribute("usuarioLogado") == null)
            return "redirect:/login";

        model.addAttribute("movimentacoes", movimentacaoRepository.findAll());
        return "app/movimentacoes/listar";
    }

    @GetMapping("/nova")
    public String novaMovimentacao(HttpSession session, Model model) {
        if (session.getAttribute("usuarioLogado") == null)
            return "redirect:/login";

        model.addAttribute("movimentacao", new Movimentacoes());

        model.addAttribute("materiais", materialRepository.findAll());
        return "app/movimentacoes/nova";
    }

    @PostMapping("/salvar")
    public String salvarMovimentacao(@ModelAttribute Movimentacoes movimentacao, HttpSession session) {
        if (session.getAttribute("usuarioLogado") == null)
            return "redirect:/login";

        // 1. Salva a movimentação
        movimentacaoRepository.save(movimentacao);

        // 2. Busca o material
        Materiais material = materialRepository.findById(movimentacao.getMaterial().getId()).orElse(null);

        if (material != null) {
            // AQUI ESTÁ A MÁGICA: Usando == e apontando para o Enum correto!
            if (movimentacao.getTipo() == Movimentacoes.TipoMovimentacao.ENTRADA) {
                material.setEstoque(material.getEstoque() + movimentacao.getQuantidade());

            } else if (movimentacao.getTipo() == Movimentacoes.TipoMovimentacao.SAIDA) {
                material.setEstoque(material.getEstoque() - movimentacao.getQuantidade());
            }

            // 3. Salva o material com o estoque atualizado
            materialRepository.save(material);
        }

        return "redirect:/app/movimentacoes";
    }

    @GetMapping("/excluir/{id}")
    public String excluirMovimentacao(@PathVariable("id") Long id, HttpSession session) {
        if (session.getAttribute("usuarioLogado") == null)
            return "redirect:/login";

        // Exclui a movimentação do histórico
        movimentacaoRepository.deleteById(id);

        return "redirect:/app/movimentacoes";
    }
}