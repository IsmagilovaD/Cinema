package ru.itis.cinema.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Customer {

    public enum Role {
        USER, ADMIN
    };

    public enum State {
        NOT_CONFIRMED, CONFIRMED, DELETED, BANNED
    };

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;

    private String lastName;

    @Enumerated(value = EnumType.STRING)
    private Role role;

    @Enumerated(value = EnumType.STRING)
    private State state;

    @OneToMany(mappedBy = "customer")
    private List<Ticket> tickets;

    @OneToMany(mappedBy = "customer")
    private List<Review> reviews;

    private String email;

    private String password;
}
