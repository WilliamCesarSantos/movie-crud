package br.ada.crud.movie;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(summary = "Create new movie")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Movie was created",
                    content = {
                            @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(oneOf = Movie.class)
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Bad request",
                    content = {
                            @Content(
                                    examples = {
                                            @ExampleObject(value = "{\"errors\": [{\"rating\": \"must be less than or equal to 10\"}],\"code\": \"BAD_REQUEST\"}")
                                    }
                            )
                    })
    })
    @PostMapping(
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    @ResponseStatus(HttpStatus.CREATED)
    public Movie create(@Valid @RequestBody Movie movie) {
        return service.insert(movie);
    }

    @Operation(summary = "List all movies")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Return all movies was registered",
                    content = {
                            @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(
                                            schema = @Schema(oneOf = Movie.class)
                                    )
                            )
                    }
            )
    })
    @GetMapping(
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    public List<Movie> list(@RequestParam(name = "title", required = false) String title) {
        return service.list(title);
    }

    @Operation(summary = "Find movie by id")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Movie was found",
                    content = {
                            @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(oneOf = Movie.class)
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Movie was not found",
                    content = {
                            @Content(
                                    examples = {
                                            @ExampleObject(value = "{\"message\": \"Not found\", \"code\": \"NOT_FOUND\"}")
                                    }
                            )
                    })
    })
    @GetMapping(
            produces = {MediaType.APPLICATION_JSON_VALUE},
            value = "/{id}"
    )
    public Movie get(@PathVariable Long id) {
        return service.findById(id)
                .orElseThrow(EntityNotFoundException::new);
    }

    @Operation(summary = "Update movie")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Movie was edited",
                    content = {
                            @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(oneOf = Movie.class)
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Movie was not found",
                    content = {
                            @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    examples = {
                                            @ExampleObject(value = "{\"message\": \"Not found\", \"code\": \"NOT_FOUND\"}")
                                    }
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Bad request",
                    content = {
                            @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    examples = {
                                            @ExampleObject(value = "{\"errors\": [{\"rating\": \"must be less than or equal to 10\"}],\"code\": \"BAD_REQUEST\"}")
                                    }
                            )
                    })
    })
    @PutMapping(
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE},
            value = "/{id}"
    )
    public Movie edit(@PathVariable Long id, @Valid @RequestBody Movie movie) {
        movie.setId(id);
        return service.insert(movie);
    }

    @Operation(summary = "Delete movie")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
                    description = "Movie was deleted"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Movie was not found",
                    content = {
                            @Content(
                                    examples = {
                                            @ExampleObject(value = "{\"message\": \"Not found\", \"code\": \"NOT_FOUND\"}")
                                    }
                            )
                    }
            )
    })
    @DeleteMapping(
            value = "/{id}"
    )
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
