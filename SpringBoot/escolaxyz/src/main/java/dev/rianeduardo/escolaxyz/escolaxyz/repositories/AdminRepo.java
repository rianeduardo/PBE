package dev.rianeduardo.escolaxyz.escolaxyz.repositories;

import org.springframework.data.repository.CrudRepository;
import dev.rianeduardo.escolaxyz.escolaxyz.models.Admin;

public interface AdminRepo extends CrudRepository<Admin, String> {
    Admin findByCpf(String cpf);
}