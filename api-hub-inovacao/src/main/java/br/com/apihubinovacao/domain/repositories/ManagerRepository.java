package br.com.apihubinovacao.domain.repositories;

import br.com.apihubinovacao.domain.models.users.Manager;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ManagerRepository extends JpaRepository<Manager, Long> {
    Optional<Manager> findByEmail(String email);
}