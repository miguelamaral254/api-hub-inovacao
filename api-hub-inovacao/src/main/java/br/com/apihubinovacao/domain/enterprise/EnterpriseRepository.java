package br.com.apihubinovacao.domain.enterprise;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface EnterpriseRepository extends JpaRepository<Enterprise, Long> , JpaSpecificationExecutor<Enterprise> {
    boolean existsByCnpj(String cnpj);
}
