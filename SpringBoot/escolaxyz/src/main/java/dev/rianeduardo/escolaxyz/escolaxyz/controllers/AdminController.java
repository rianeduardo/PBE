package dev.rianeduardo.escolaxyz.escolaxyz.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import dev.rianeduardo.escolaxyz.escolaxyz.models.Admin;
import dev.rianeduardo.escolaxyz.escolaxyz.repositories.AdminRepo;
import dev.rianeduardo.escolaxyz.escolaxyz.repositories.VerificaCadastroAdminRepo;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AdminController {
    @Autowired
    AdminRepo repoAdm;

    @Autowired
    VerificaCadastroAdminRepo repoVerificacao;

    boolean acessoAdm = false;

    @GetMapping("/cadastro-adm")
    public String abrirLoginAdm() {
        return "cadastro/cadastroAdmin";
    }

    @PostMapping("/cadastrar-adm")
    public ModelAndView cadastrarAdmin(Admin adm, RedirectAttributes rAttributes) {
        ModelAndView mvLogin = new ModelAndView("redirect:/login-adm");
        boolean verificarCpf = repoVerificacao.existsById(adm.getCpf());
        if (verificarCpf == true) {
            repoAdm.save(adm);
            String mensagem = "Cadastro Realizado com Sucesso!";
            System.out.println(mensagem);
            rAttributes.addFlashAttribute("msg", mensagem);
            rAttributes.addFlashAttribute("classe", "verde");
        } else {
            String mensagemErro = "Cadastro Inválido!";
            System.out.println(mensagemErro);
            rAttributes.addFlashAttribute("msg", mensagemErro);
            rAttributes.addFlashAttribute("classe", "vermelho");
        }

        return mvLogin;
    }

    @GetMapping("/login-adm")
    public String abrirLoginAdmin() {
        return "login/loginAdmin";
    }

    @PostMapping("/acesso-adm")
    public ModelAndView acessoAdm(@RequestParam String cpf, @RequestParam String senha,
            RedirectAttributes rAttributes) {
        ModelAndView mvAreaAdmin = new ModelAndView("redirect:/interna-adm");
        boolean verificaCpf = repoAdm.existsById(cpf);
        boolean verificaSenha = repoAdm.findByCpf(cpf).getSenha().equals(senha);
        if (verificaSenha && verificaCpf) {
            acessoAdm = true;
        } else {
            String mensagem = "CPF ou Senha Incorreto";
            System.out.println(mensagem);
            rAttributes.addFlashAttribute("msg", mensagem);
            rAttributes.addFlashAttribute("classe", "vermelho");
            mvAreaAdmin.setViewName("redirect:/login-adm");
        }

        return mvAreaAdmin;
    }

    @GetMapping("/interna-adm")
    public ModelAndView abrirAreaAdmin(RedirectAttributes rAttributes) {
        String output = "";
        if (acessoAdm) {
            output = "admin/areaInterna";
        } else {
            String mensagem = "Acesso não Permitido";
            System.out.println(mensagem);
            rAttributes.addFlashAttribute("msg", mensagem);
            rAttributes.addFlashAttribute("classe", "vermelha");
            output = "redirect:/";
        }
        ModelAndView mvAcessoAdm = new ModelAndView(output);
        return mvAcessoAdm;
    }

    @GetMapping("/logout-adm")
    public String logoutAdmin() {
        acessoAdm = false;
        return "redirect:/";
    }

}
