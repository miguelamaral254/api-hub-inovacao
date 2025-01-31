package br.com.apihubinovacao.domain.models;

import br.com.apihubinovacao.domain.models.users.Manager;
import jakarta.persistence.*;


import java.time.LocalDate;

@Entity
@Table(name = "publish")
public class Publish {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;
    private String acessLink;
    private String photoLink;
    private LocalDate initialDate;
    private LocalDate finalDate;

    @ManyToOne
    @JoinColumn(name = "manager_id")
    private Manager manager;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAcessLink() {
        return acessLink;
    }

    public void setAcessLink(String acessLink) {
        this.acessLink = acessLink;
    }

    public String getPhotoLink() {
        return photoLink;
    }

    public void setPhotoLink(String photoLink) {
        this.photoLink = photoLink;
    }

    public LocalDate getFinalDate() {
        return finalDate;
    }

    public void setFinalDate(LocalDate finalDate) {
        this.finalDate = finalDate;
    }

    public LocalDate getInitialDate() {
        return initialDate;
    }

    public void setInitialDate(LocalDate initialDate) {
        this.initialDate = initialDate;
    }

    public Manager getManager() {
        return manager;
    }

    public void setManager(Manager manager) {
        this.manager = manager;
    }
}
