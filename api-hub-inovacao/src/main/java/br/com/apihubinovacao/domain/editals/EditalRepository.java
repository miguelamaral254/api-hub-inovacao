package br.com.apihubinovacao.domain.editals;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface EditalRepository extends JpaRepository<Edital, Long>, JpaSpecificationExecutor<Edital> {

    @Query("SELECT e FROM Edital e LEFT JOIN FETCH e.idUser")
    Page<Edital> findAllWithUser(Specification<Edital> specification, Pageable pageable);
}