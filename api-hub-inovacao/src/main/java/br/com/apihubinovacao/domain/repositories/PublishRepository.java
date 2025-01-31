package br.com.apihubinovacao.domain.repositories;

import br.com.apihubinovacao.domain.models.Publish;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PublishRepository extends JpaRepository<Publish, Long> {
}
