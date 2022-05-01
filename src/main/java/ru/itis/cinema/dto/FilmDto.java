package ru.itis.cinema.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itis.cinema.models.Film;
import ru.itis.cinema.models.Genre;

import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FilmDto {

    private String name;
    private List<String> genres;

    public static FilmDto from(Film film){
        return FilmDto.builder()
                .name(film.getName())
                .genres(film.getGenres().stream().map(Genre::getName).collect(Collectors.toList()))
                .build();
    }

    public static List<FilmDto> from(List<Film> films){
        return films.stream().map(FilmDto::from).collect(Collectors.toList());
    }
}
