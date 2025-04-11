package br.com.apihubinovacao.domain.projects;

import br.com.apihubinovacao.core.BaseEntity;
import br.com.apihubinovacao.domain.coauthor.Coauthor;
import br.com.apihubinovacao.core.StatusSolicitation;
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

    @Column(nullable = false, length = 255)
    private String title;

    @Column(nullable = false, length = 500)
    private String description;

    @Column(nullable = false, length = 255)
    private String urlPhoto;

    @Column(nullable = true, length = 255)
    private String siteLink;

    @Column(nullable = false, length = 255)
    private String thematicArea;

    @Column(nullable = false, length = 255)
    private String course;

    @Column(length = 1000)
    private String problem;

    @Column(length = 2000)
    private String generalObjective;

    @Column(length = 1000)
    private String specificObjective;

    @Column(length = 2000)
    private String expectedResults;

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

    @Column(nullable= true)
    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Coauthor> coauthors;

}