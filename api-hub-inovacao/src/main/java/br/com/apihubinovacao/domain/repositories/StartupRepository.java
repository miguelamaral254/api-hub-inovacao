package br.com.apihubinovacao.domain.repositories;

import br.com.apihubinovacao.domain.models.projects.Startup;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StartupRepository extends JpaRepository<Startup, Long> {
}
