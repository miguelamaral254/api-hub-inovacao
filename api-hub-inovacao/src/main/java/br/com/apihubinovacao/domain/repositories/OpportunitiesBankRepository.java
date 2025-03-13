package br.com.apihubinovacao.domain.repositories;

import br.com.apihubinovacao.domain.enums.StatusSolicitation;
import br.com.apihubinovacao.domain.models.projects.OpportunitiesBank;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface OpportunitiesBankRepository extends JpaRepository<OpportunitiesBank, Long> {
    Page<OpportunitiesBank> findByStatusAndFlagActive(StatusSolicitation status, boolean flagActive, Pageable pageable);
    Page<OpportunitiesBank> findByIdManager(Long idManager, Pageable pageable);

    Page<OpportunitiesBank> findByPartnerCompanyName(String companyName, Pageable pageable);
    List<OpportunitiesBank> findByStatusAndFlagActive(StatusSolicitation status, boolean flagActive);

    Optional<OpportunitiesBank> findById(Long id);

}