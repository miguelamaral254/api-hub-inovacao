package br.com.apihubinovacao.domain.repositories;

import br.com.apihubinovacao.domain.enums.StatusSolicitation;
import br.com.apihubinovacao.domain.models.projects.AcademicProject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AcademicProjectRepository extends JpaRepository<AcademicProject, Long> {

    Page<AcademicProject> findAllByStatus(StatusSolicitation status, Pageable pageable);
    Page<AcademicProject> findByAuthorEmail(String authorEmail, Pageable pageable);

    boolean existsByTitle(String title);
}