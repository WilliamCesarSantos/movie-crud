package br.ada.crud.movie;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MovieService {

    private final MovieRepository repository;

    public MovieService(MovieRepository repository) {
        this.repository = repository;
    }

    public Optional<Movie> findById(Long id) {
        return repository.findById(id);
    }

    public List<Movie> list(String title) {
        List<Movie> movies = null;
        if (title != null) {
            movies = repository.findByTitle(title);
        } else {
            movies = repository.findAll();
        }
        return movies;
    }

    public Movie insert(Movie movie) {
        return repository.save(movie);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

}