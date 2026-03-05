package rian.teste_thymeleaf;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class indexController {
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView abrirIndex() {
        ModelAndView mv = new ModelAndView("index");
        String msg = "Olá, Visitante!";
        mv.addObject("msg", msg);
        return mv;
    }

    @GetMapping("/sobre")
    public String abrirSobre() {
        return "sobre";
    }

    @GetMapping("/produto")
    public String abrirProdutos() {
        return "produtos";
    }

    @GetMapping("/contato")
    public String abrirContato() {
        return "contato";
    }

    @PostMapping("/home")
    public ModelAndView postHome(@RequestParam("nome") String nome) {
        ModelAndView mvInput = new ModelAndView("index");
        String mensagemInput = "Olá, " + nome + "!";
        mvInput.addObject("msg", mensagemInput);
        mvInput.addObject("nome", "");
        return mvInput;
    }

}