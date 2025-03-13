package br.com.apihubinovacao.domain.startup;

import br.com.apihubinovacao.core.BaseEntity;
import br.com.apihubinovacao.core.StatusSolicitation;
import br.com.apihubinovacao.domain.users.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tbl_startup")
public class Startup extends BaseEntity {

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description", columnDefinition = "TEXT", nullable = false)
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "cnpj", unique = true, length = 14)
    private String cnpj;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menager_id")
    private User userMenager;

    @Column(name = "feedback", columnDefinition = "TEXT")
    private String feedback;

    @Column(nullable = false)
    private StatusSolicitation status;

    @Column(name = "justification", columnDefinition = "TEXT")
    private String justification;
}

