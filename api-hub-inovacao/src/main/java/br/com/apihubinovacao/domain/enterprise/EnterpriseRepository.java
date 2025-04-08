package br.com.apihubinovacao.domain.enterprise;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface EnterpriseRepository extends JpaRepository<Enterprise, Long> , JpaSpecificationExecutor<Enterprise> {
    boolean existsByCnpj(String cnpj);

    Enterprise findByCnpj(String cnpj);

    Enterprise findByCnpjAndIdNot(String cnpj, Long id);

    Optional<Enterprise> findByEmail(String email);

    boolean existsByEmail(@NotBlank @Email(message = "Email com formato inválido") String email);
}
