package br.com.apihubinovacao.domain.editals;

import br.com.apihubinovacao.core.BaseEntity;
import br.com.apihubinovacao.domain.users.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tbl_editals")
public class Edital extends BaseEntity {

    private String title;

    private String description;

    private String acessLink;

    private LocalDate initialDate;

    private LocalDate finalDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User idUser;


}
