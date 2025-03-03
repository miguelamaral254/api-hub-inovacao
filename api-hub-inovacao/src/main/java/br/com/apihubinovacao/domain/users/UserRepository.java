package br.com.apihubinovacao.domain.users;

import br.com.apihubinovacao.core.CharacterDataQuery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long>  , JpaSpecificationExecutor<User> , CharacterDataQuery {

    public Optional<User> findByCpf(String cpf);
    public Optional<User> findByCnpj(String cnpj);
    public Optional<User> findByRegistration(String registration);
    public Optional<User> findByEmail(String email);

}
