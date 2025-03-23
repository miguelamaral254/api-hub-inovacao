package br.com.apihubinovacao.domain.startup;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface StartupRepository extends JpaRepository<Startup, Long>, JpaSpecificationExecutor<Startup> {

    boolean existsByTitleAndIdNot(String title, Long id);

    @Override
    Page<Startup> findAll(Specification<Startup> spec, Pageable pageable);

    boolean existsByTitle(String title);

    boolean existsByCnpj(String cnpj);

    List<Startup> findByTitleContainingIgnoreCase(String keyword);
}
