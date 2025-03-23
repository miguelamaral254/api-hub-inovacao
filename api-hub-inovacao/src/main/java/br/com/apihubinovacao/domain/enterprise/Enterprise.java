package br.com.apihubinovacao.domain.enterprise;

import br.com.apihubinovacao.core.BaseEntity;
import br.com.apihubinovacao.domain.address.Address;
import br.com.apihubinovacao.domain.phone.Phone;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tbl_enterprises")
public class Enterprise extends BaseEntity {

    private String nomeEmpresa;
    private String cnpj;
    private String setorAtuacao;

    private String phone;

    @OneToOne(mappedBy = "enterprise", cascade = CascadeType.ALL, orphanRemoval = true)
    private Address address;

    private String reprentantName;

    private String reprentantPosition;

    private String reprentantEmail;

    private String reprentantPhone;


}