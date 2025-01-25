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
    @JoinColumn(name = "USER_idUSER", nullable = false)
    private User user;

    // Getters and Setters
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
