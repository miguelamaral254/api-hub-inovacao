
package br.com.apihubinovacao.domain.models;

import br.com.apihubinovacao.domain.enums.ProjectType;
import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "SUBMISSION")
public class Submission extends AbstractProject {

    @Enumerated(EnumType.STRING)
    @Column(name = "project_type", length = 100, nullable = false)
    private ProjectType projectType;

    @OneToMany(mappedBy = "submission", cascade = CascadeType.ALL)
    private List<SubmissionDocument> documents;

    @OneToMany(mappedBy = "submission", cascade = CascadeType.ALL)
    private List<CoauthorSubmission> coauthors;

    @OneToOne(mappedBy = "submission", cascade = CascadeType.ALL)
    private Solicitation solicitation;

    public ProjectType getProjectType() {
        return projectType;
    }

    public void setProjectType(ProjectType projectType) {
        this.projectType = projectType;
    }

    public List<SubmissionDocument> getDocuments() {
        return documents;
    }

    public void setDocuments(List<SubmissionDocument> documents) {
        this.documents = documents;
    }

    public List<CoauthorSubmission> getCoauthors() {
        return coauthors;
    }

    public void setCoauthors(List<CoauthorSubmission> coauthors) {
        this.coauthors = coauthors;
    }

    public Solicitation getSolicitation() {
        return solicitation;
    }

    public void setSolicitation(Solicitation solicitation) {
        this.solicitation = solicitation;
    }
}
