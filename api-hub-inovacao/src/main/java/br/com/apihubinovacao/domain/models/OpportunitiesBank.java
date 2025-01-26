package br.com.apihubinovacao.domain.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "OPPORTUNITIES_BANK")
public class OpportunitiesBank extends Abstractproject {


    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    @Column(name = "validation_date")
    private LocalDate validationDate;

    @Column(name = "award", length = 300)
    private String award;

    @Column(name = "solved_by", length = 300)
    private String solvedBy;

    @OneToMany(mappedBy = "opportunitiesBank")
    private List<Solicitation> submissions;

}
