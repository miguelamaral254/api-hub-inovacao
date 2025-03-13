package br.com.apihubinovacao.domain.models.users;

import br.com.apihubinovacao.domain.models.projects.OpportunitiesBank;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "PARTNER_COMPANY")
public class PartnerCompany extends User implements UserBase {

    @Column(name = "cnpj", length = 20, nullable = false, unique = true)
    private String cnpj;

    @OneToMany(mappedBy = "partnerCompany", fetch = FetchType.LAZY)
    private List<Phone> phones;

    @OneToMany(mappedBy = "partnerCompany", fetch = FetchType.LAZY)
    private List<OpportunitiesBank> opportunitiesBanks;

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    @Override
    public List<Phone> getPhones() {
        return phones != null ? phones : List.of();
    }

    public List<OpportunitiesBank> getOpportunitiesBanks() {
        return opportunitiesBanks;
    }

    public void setOpportunitiesBanks(List<OpportunitiesBank> opportunitiesBanks) {
        this.opportunitiesBanks = opportunitiesBanks;
    }
}