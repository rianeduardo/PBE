package dev.rianeduardo.escolaxyz.escolaxyz.models;

import java.io.Serializable;
import jakarta.persistence.*;
import lombok.Data;

// Entidade do banco
@Entity
@Data
public class Admin implements Serializable {
    @Id
    private String cpf;

    private String nome;
    private String email;
    private String senha;

    // Boilerplate feito com o Lombok
}
