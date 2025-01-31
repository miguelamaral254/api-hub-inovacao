package br.com.apihubinovacao.domain.repositories;

import br.com.apihubinovacao.domain.enums.StatusSolicitation;
import br.com.apihubinovacao.domain.models.projects.OpportunitiesBank;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface OpportunitiesBankRepository extends JpaRepository<OpportunitiesBank, Long> {

    List<OpportunitiesBank> findByPartnerCompanyName(String companyName);
    List<OpportunitiesBank> findByStatusAndFlagActive(StatusSolicitation status, boolean flagActive);

    Optional<OpportunitiesBank> findById(Long id);

}