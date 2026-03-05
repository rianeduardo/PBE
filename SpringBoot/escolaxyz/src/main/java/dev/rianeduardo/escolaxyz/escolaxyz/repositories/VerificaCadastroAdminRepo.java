package dev.rianeduardo.escolaxyz.escolaxyz.repositories;

import org.springframework.data.repository.CrudRepository;
import dev.rianeduardo.escolaxyz.escolaxyz.models.VerificaCadastroAdmin;

public interface VerificaCadastroAdminRepo extends CrudRepository<VerificaCadastroAdmin, String> {
    VerificaCadastroAdmin findByCpf(String cpf);
}
