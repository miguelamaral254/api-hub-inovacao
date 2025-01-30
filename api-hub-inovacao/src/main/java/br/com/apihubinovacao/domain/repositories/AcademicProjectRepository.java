package br.com.apihubinovacao.domain.repositories;

import br.com.apihubinovacao.domain.models.AcademicProject;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AcademicProjectRepository extends JpaRepository<AcademicProject, Long> {

    boolean existsByTitle(String title);
}