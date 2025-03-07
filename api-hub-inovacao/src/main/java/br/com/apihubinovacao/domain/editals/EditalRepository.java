package br.com.apihubinovacao.domain.editals;

import br.com.apihubinovacao.core.GenericDataQuery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface EditalRepository extends JpaRepository<Edital, Long>, JpaSpecificationExecutor<Edital> { }
