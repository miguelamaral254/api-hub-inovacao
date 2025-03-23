package br.com.apihubinovacao.domain.users;

import br.com.apihubinovacao.core.GenericDataQuery;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long>  , JpaSpecificationExecutor<User> , GenericDataQuery {


    public Optional<User> findByEmail(String email);

    boolean existsByRegistration(@NotBlank String registration);

    boolean existsByCpf(@Pattern(regexp = "\\d{11}", message = "CPF deve ter 11 dígitos") String cpf);

    boolean existsByCnpj(@Pattern(regexp = "\\d{14}", message = "CNPJ deve ter 14 dígitos") String cnpj);

    boolean existsByEmail(@NotBlank @Email(message = "Email com formato inválido") String email);

    boolean existsByCpfAndIdNot(@Pattern(regexp = "\\d{11}", message = "CPF deve ter 11 dígitos") String cpf, Long id);

    boolean existsByCnpjAndIdNot(@Pattern(regexp = "\\d{14}", message = "CNPJ deve ter 14 dígitos") String cnpj, Long id);

    boolean existsByRegistrationAndIdNot(@NotBlank String registration, Long id);

    boolean existsByEmailAndIdNot(@NotBlank @Email(message = "Email com formato inválido") String email, Long id);
}
