package br.com.apihubinovacao.domain.repositories;

import br.com.apihubinovacao.domain.models.users.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdminRepository extends JpaRepository<Admin, Long> {
    Optional<Admin> findByEmail(String email);
    Optional<Admin> findByCnpj(String cnpj);
}