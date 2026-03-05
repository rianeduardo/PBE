package com.example.rh.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import com.example.rh.repositories.FuncionarioRepository;
import com.example.rh.models.Funcionario;

@Controller
public class ListaFuncionarioController {
    @Autowired
    private FuncionarioRepository fr;

    @RequestMapping(value = "/listar", method = RequestMethod.GET)
    public ModelAndView listarfuncionario() {
        ModelAndView mv = new ModelAndView("funcionario/listarFuncionarios");
        mv.addObject("funcionarios", fr.findAll());
        return mv;
    }

    @RequestMapping(value = "/deletarfuncionario/{id}", method = RequestMethod.GET)
    public String deletarFuncionario(@PathVariable("id") long id) {
        fr.delete(fr.findById(id));
        return "redirect:/listar";
    }

    @RequestMapping(value = "/editarfuncionario/{id}", method = RequestMethod.GET)
    public ModelAndView abrireditarfuncionario(@PathVariable("id") long id) {
        ModelAndView mv = new ModelAndView("funcionario/editarFuncionarios");
        mv.addObject("funcionario", fr.findById(id));
        return mv;
    }

    @RequestMapping(value = "/editarfuncionario/{id}", method = RequestMethod.POST)
    public String updateFuncionario(Funcionario funcionario) {
        fr.save(funcionario);
        return "redirect:/listar";
    }
}