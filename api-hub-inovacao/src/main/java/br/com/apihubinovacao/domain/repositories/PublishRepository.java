package br.com.apihubinovacao.domain.repositories;

import br.com.apihubinovacao.domain.models.Publish;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface PublishRepository extends JpaRepository<Publish, Long> {
    Optional<Publish> findByFinalDate(LocalDate finalDate);
}
