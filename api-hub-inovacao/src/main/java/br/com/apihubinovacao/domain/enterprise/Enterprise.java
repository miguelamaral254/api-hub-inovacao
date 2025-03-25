package br.com.apihubinovacao.domain.enterprise;

import br.com.apihubinovacao.core.BaseEntity;
import br.com.apihubinovacao.domain.address.Address;
import br.com.apihubinovacao.domain.phone.Phone;
import br.com.apihubinovacao.domain.users.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
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

    @Pattern(regexp = "\\d{14}", message = "CNPJ deve ter 14 dígitos")
    @Column(unique = true)
    private String cnpj;

    private String setorAtuacao;

    private String phone;

    @NotBlank
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

    @NotBlank
    @Column(nullable = false)
    @Email(message = "Email com formato inválido")
    private String email;

    @NotNull
    @Column(nullable = false)
    private String password;

    @OneToOne(mappedBy = "enterprise", cascade = CascadeType.ALL, orphanRemoval = true)
    private Address address;

    private String reprentantName;

    private String reprentantPosition;

    @NotBlank
    @Column(nullable = false)
    @Email(message = "Email com formato inválido")
    private String reprentantEmail;

    private String reprentantPhone;


}