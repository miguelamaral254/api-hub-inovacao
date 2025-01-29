package br.com.apihubinovacao.domain.models;

import jakarta.persistence.*;

@Entity
@Table(name = "PHONE")
public class Phone {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idPHONE")
    private Long id;

    @Column(name = "number", length = 45, nullable = false)
    private String number;

    @ManyToOne
    @JoinColumn(name = "ADMIN_idUSER")
    private Admin admin;

    @ManyToOne
    @JoinColumn(name = "MANAGER_idUSER")
    private Manager manager;

    @ManyToOne
    @JoinColumn(name = "STUDENT_idUSER")
    private Student student;

    @ManyToOne
    @JoinColumn(name = "PROFESSOR_idUSER")
    private Professor professor;

    @ManyToOne
    @JoinColumn(name = "PARTNER_COMPANY_idUSER")
    private PartnerCompany partnerCompany;

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Admin getAdmin() {
        return admin;
    }

    public void setAdmin(Admin admin) {
        this.admin = admin;
    }

    public Manager getManager() {
        return manager;
    }

    public void setManager(Manager manager) {
        this.manager = manager;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Professor getProfessor() {
        return professor;
    }

    public void setProfessor(Professor professor) {
        this.professor = professor;
    }

    public PartnerCompany getPartnerCompany() {
        return partnerCompany;
    }

    public void setPartnerCompany(PartnerCompany partnerCompany) {
        this.partnerCompany = partnerCompany;
    }
}