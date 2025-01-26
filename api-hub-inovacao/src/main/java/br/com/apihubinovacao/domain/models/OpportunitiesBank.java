package br.com.apihubinovacao.domain.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "OPPORTUNITIES_BANK")
public class OpportunitiesBank extends AbstractProject {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    @Column(name = "validation_date")
    private LocalDate validationDate;

    @Column(name = "award", length = 300)
    private String award;

    @Column(name = "solved_by", length = 300)
    private String solvedBy;

    @OneToMany(mappedBy = "opportunitiesBank")
    private List<Solicitation> submissions;

    public LocalDate getValidationDate() {
        return validationDate;
    }

    public void setValidationDate(LocalDate validationDate) {
        this.validationDate = validationDate;
    }

    public String getAward() {
        return award;
    }

    public void setAward(String award) {
        this.award = award;
    }

    public String getSolvedBy() {
        return solvedBy;
    }

    public void setSolvedBy(String solvedBy) {
        this.solvedBy = solvedBy;
    }

    public List<Solicitation> getSubmissions() {
        return submissions;
    }

    public void setSubmissions(List<Solicitation> submissions) {
        this.submissions = submissions;
    }
}
