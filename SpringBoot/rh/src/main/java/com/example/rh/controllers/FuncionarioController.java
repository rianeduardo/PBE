package com.example.rh.controllers;

import com.example.rh.repositories.FuncionarioRepository;
import com.example.rh.models.Funcionario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class FuncionarioController {
    @Autowired
    private FuncionarioRepository fr;

    @RequestMapping(value = "/funcionario", method = RequestMethod.GET)
    public String abrirFuncionario() {
        return "funcionario/funcionario";
    }

    @RequestMapping(value = "/funcionario", method = RequestMethod.POST)
    public String gravarFuncionario(Funcionario funcionario) {
        fr.save(funcionario);
        return "redirect:/funcionario";
    }

}
