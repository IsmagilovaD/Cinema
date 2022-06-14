package ru.itis.cinema.repositories;

public interface BlackListRepository {

    void save(String token);

    boolean exists(String token);

}
