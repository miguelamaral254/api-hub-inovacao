package br.com.apihubinovacao.domain.models;

import jakarta.persistence.*;

@Entity
@Table(name = "COAUTHOR_SUBMISSION")
public class CoauthorSubmission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idCOAUTHOR_SUBMISSION")
    private Long id;
    @Column(name = "name", length = 500, nullable = false)
    private String name;
    @Column(name = "email", length = 200, nullable = false)
    private String email;
    @Column(name = "phone", length = 50)
    private String phone;

    @ManyToOne
    @JoinColumn(name = "SUBMISSION_idSUBMISSION", nullable = false)
    private Submission submission;
}
