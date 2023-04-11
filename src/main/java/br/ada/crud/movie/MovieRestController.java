package br.ada.crud.movie;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/movies")
public class MovieRestController {

    private MovieService service;

    public MovieRestController(MovieService service) {
        this.service = service;
    }

    @PostMapping(
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    @ResponseStatus(HttpStatus.CREATED)
    public Movie create(@Valid @RequestBody Movie movie) {
        return service.insert(movie);
    }

    @GetMapping(
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    public List<Movie> list() {
        return service.list();
    }

    @GetMapping(
            produces = {MediaType.APPLICATION_JSON_VALUE},
            value = "/{id}"
    )
    public Movie get(@PathVariable Long id) {
        return service.findById(id)
                .orElseThrow(EntityNotFoundException::new);
    }

    @PutMapping(
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE},
            value = "/{id}"
    )
    public Movie edit(@PathVariable Long id, @Valid @RequestBody Movie movie) {
        movie.setId(id);
        return service.insert(movie);
    }

    @DeleteMapping(
            value = "/{id}"
    )
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
