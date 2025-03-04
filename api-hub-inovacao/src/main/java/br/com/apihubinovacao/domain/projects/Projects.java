package br.com.apihubinovacao.domain.projects;

import br.com.apihubinovacao.core.BaseEntity;
import br.com.apihubinovacao.domain.coauthor.Coauthor;
import br.com.apihubinovacao.domain.enums.ProjectType;
import br.com.apihubinovacao.domain.enums.StatusSolicitation;
import br.com.apihubinovacao.domain.enums.TypeAP;
import br.com.apihubinovacao.domain.users.User;
import jakarta.persistence.*;
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
@Table(name = "tbl_projects")
public class Projects extends BaseEntity {

    private String title;
    private String description;
    private String urlPhoto;
    private String pdfLink;
    private String siteLink;

    @Enumerated(EnumType.STRING)
    private ProjectType projectType;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private StatusSolicitation status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_manager", nullable = true)
    private User idManager;

    @Column(name = "feedback", nullable = true)
    private String feedback;

    @Column(name = "justification", nullable = true)
    private String justification;

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Coauthor> coauthors;

}