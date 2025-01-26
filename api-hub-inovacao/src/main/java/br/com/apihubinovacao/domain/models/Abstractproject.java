package br.com.apihubinovacao.domain.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;

import java.time.LocalDate;

@MappedSuperclass
public class Abstractproject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", length = 300, nullable = false)
    private String name;

    @Column(name = "descrition", length = 500, nullable = false)
    private String description;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    @Column(name = "creation_date",nullable = false)
    private LocalDate creationDate;

    @Column(name = "url_photo", length = 1000)
    private String urlPhoto;

    @Column(name = "responsible_institution", length = 200, nullable = false)
    private String responsibleInstitution;

    @Column(name = "site", length = 200)
    private String site;

    @Column(name = "status", length = 100)
    private String status;

}
