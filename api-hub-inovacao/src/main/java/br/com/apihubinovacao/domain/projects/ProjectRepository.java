package br.com.apihubinovacao.domain.projects;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ProjectRepository extends JpaRepository<Projects, Long>, JpaSpecificationExecutor<Projects> {
    boolean existsByTitleAndIdNot(String title, Long id);

    @Override
    Page<Projects> findAll(Specification<Projects> spec, Pageable pageable);

}
