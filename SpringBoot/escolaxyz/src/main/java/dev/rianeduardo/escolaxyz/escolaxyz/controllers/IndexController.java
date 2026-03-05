package dev.rianeduardo.escolaxyz.escolaxyz.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

    @GetMapping("/home")
    public String abrirIndex() {
        return "index";
    }

    @GetMapping("/contato")
    public String abrirContato() {
        return "pages/contato";
    }

    @GetMapping("/sobre")
    public String abrirSobre() {
        return "pages/sobre";
    }

}
