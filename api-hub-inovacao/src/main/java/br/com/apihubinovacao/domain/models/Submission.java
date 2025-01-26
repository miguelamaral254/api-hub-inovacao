package br.com.apihubinovacao.domain.models;

import br.com.apihubinovacao.domain.enums.ProjectType;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "SUBMISSION")
public class Submission extends Abstractproject {

    @Enumerated(EnumType.STRING)
    @Column(name = "project_type", length = 100, nullable = false)
    private ProjectType projectType;

    @OneToMany(mappedBy = "submission", cascade = CascadeType.ALL)
    private List<SubmissionDocument> document;

    @OneToMany(mappedBy = "submission", cascade = CascadeType.ALL)
    private List<CoauthorSubmission> coauthor;

    @OneToOne(mappedBy = "submission", cascade = CascadeType.ALL)
    private Solicitation solicitation;


}
