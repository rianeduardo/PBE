package dev.rianeduardo.escolaxyz.escolaxyz.models;

import java.io.Serializable;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
public class VerificaCadastroAdmin implements Serializable {
    @Id
    private String cpf;
    private String nome;
}