package br.com.apihubinovacao.domain.repositories;

import br.com.apihubinovacao.domain.models.users.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    Optional<Student> findByEmail(String email);
    Optional<Student> findByCpf(String cpf);
    Optional<Student> findByRegistration(String registration);

  
}