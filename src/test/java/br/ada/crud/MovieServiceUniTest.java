package br.ada.crud;

import br.ada.crud.movie.Movie;
import br.ada.crud.movie.MovieRepository;
import br.ada.crud.movie.MovieService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class MovieServiceUniTest {

    private MovieRepository repository;
    private MovieService service;

    @BeforeEach
    public void setup() {
        repository = Mockito.mock(MovieRepository.class);
        service = new MovieService(repository);
    }

    @Test
    public void cadastrarUmNovoFilme() {
        service.insert(new Movie());
    }

    @Test
    public void cadastrarUmNovoFilme_deveChamarORepositorio() {
        Movie movie = new Movie();

        service.insert(movie);

        Mockito.verify(repository, Mockito.times(1))
                .save(movie);
    }

}
