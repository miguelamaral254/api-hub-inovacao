package br.com.apihubinovacao.domain.projects;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface ProjectRepository extends JpaRepository<Projects, Long>, JpaSpecificationExecutor<Projects> {
    boolean existsByTitleAndIdNot(String title, Long id);
    @Override

    @Query("SELECT DISTINCT p FROM Projects p LEFT JOIN FETCH p.coauthors")
    Page<Projects> findAll(Specification<Projects> spec, Pageable pageable);
}
