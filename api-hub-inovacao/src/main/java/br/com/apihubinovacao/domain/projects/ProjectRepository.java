package br.com.apihubinovacao.domain.projects;

import br.com.apihubinovacao.domain.users.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ProjectRepository extends JpaRepository<Projects, Long>, JpaSpecificationExecutor<Projects> {
    boolean existsByTitleAndIdNot(String title, Long id);
    public Optional<Projects> findByTitle(String title);
//    public Optional<Projects> findByRegistration(String registration);
  //  public Optional<Projects> findByEmail(String email);
    @Override
    @Query("SELECT DISTINCT p FROM Projects p LEFT JOIN FETCH p.coauthors")
    Page<Projects> findAll(Specification<Projects> spec, Pageable pageable);

    @Query("SELECT DISTINCT p FROM Projects p LEFT JOIN FETCH p.coauthors WHERE p.title LIKE :title")
    Page<Projects> findAllByTitle(@Param("title") String title, Pageable pageable);
}
