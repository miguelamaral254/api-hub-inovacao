package br.com.apihubinovacao.domain.repositories;


import br.com.apihubinovacao.domain.models.Phone;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PhoneRepository extends JpaRepository<Phone, Long> {}
