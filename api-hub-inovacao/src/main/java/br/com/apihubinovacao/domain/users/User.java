package br.com.apihubinovacao.domain.users;

import br.com.apihubinovacao.core.BaseEntity;
import br.com.apihubinovacao.domain.phone.Phone;
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
@Entity
@Table(name = "tbl_users")
@AllArgsConstructor
@NoArgsConstructor
public class User extends BaseEntity {

    @NotBlank
    @Column(nullable = false)
    private String name;

    @NotBlank
    @Column(nullable = false)
    private String registration;


    @NotBlank
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;


    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Phone> phones;

    @NotBlank
    @Column(nullable = false)
    @Email(message = "Email com formato inválido")
    private String email;

    @NotNull
    @Column(nullable = false)
    private String password;

    @Pattern(regexp = "\\d{11}", message = "CPF deve ter 11 dígitos")
    @Column(unique = true)
    private String cpf;

    @Pattern(regexp = "\\d{14}", message = "CNPJ deve ter 14 dígitos")
    @Column(unique = true)
    private String cnpj;

    public void validateDocument() {
        if (role != null) {
            switch (role) {
                case STUDENT, PROFESSOR, MANAGER -> {
                    if (cpf == null || !cpf.matches("\\d{11}")) {
                        throw new IllegalArgumentException("Usuários com essa role devem ter CPF válido.");
                    }
                    cnpj = null;
                }
                case  ADMIN -> {
                    if (cnpj == null || !cnpj.matches("\\d{14}")) {
                        throw new IllegalArgumentException("Usuários com essa role devem ter CNPJ válido.");
                    }
                    cpf = null;
                }
                default -> throw new IllegalArgumentException("Role inválida.");
            }
        }
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        validateDocument();
    }

    @Override
    protected void onUpdate() {
        super.onUpdate();
        validateDocument();
    }
}