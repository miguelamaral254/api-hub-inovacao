package br.com.apihubinovacao.domain.coauthor;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CoauthorRepository extends JpaRepository<Coauthor, Long> , JpaSpecificationExecutor<Coauthor> { }
