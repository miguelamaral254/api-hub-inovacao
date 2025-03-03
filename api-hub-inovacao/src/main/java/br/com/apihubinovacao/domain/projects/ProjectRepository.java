package br.com.apihubinovacao.domain.projects;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ProjectRepository extends JpaRepository<Projects, Long>, JpaSpecificationExecutor<Projects> { }
