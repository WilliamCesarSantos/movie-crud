package br.ada.crud.movie;

import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MovieService {

    private final MovieRepository repository;

    public MovieService(MovieRepository repository) {
        this.repository = repository;
    }

    public Page<Movie> list(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public Optional<Movie> findById(Long id) {
        return repository.findById(id);
    }

    public List<Movie> list() {
        return repository.findAll();
    }

    public Movie insert(Movie movie) {
        return repository.save(movie);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

}