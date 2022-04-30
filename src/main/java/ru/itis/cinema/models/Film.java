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
public class Film {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToMany(mappedBy = "films")
    private List<Genre> genres;

    @OneToMany(mappedBy = "film")
    private List<Session> sessions;

    @OneToMany(mappedBy = "film")
    private List<Review> reviews;
}
