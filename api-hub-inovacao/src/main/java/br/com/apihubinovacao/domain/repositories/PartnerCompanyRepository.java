package br.com.apihubinovacao.domain.repositories;

import br.com.apihubinovacao.domain.models.users.PartnerCompany;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PartnerCompanyRepository extends JpaRepository<PartnerCompany, Long> {
    Optional<PartnerCompany> findByEmail(String email);
}