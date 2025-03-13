package br.com.apihubinovacao.domain.repositories;

import br.com.apihubinovacao.domain.enums.StatusSolicitation;
import br.com.apihubinovacao.domain.models.projects.Startup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StartupRepository extends JpaRepository<Startup, Long> {

    List<Startup> findByStatus(StatusSolicitation status);

}