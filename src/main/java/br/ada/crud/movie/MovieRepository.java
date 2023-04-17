package br.ada.crud.movie;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MovieRepository extends JpaRepository<Movie, Long> {

    @Query("Select m from Movie m where m.title like %:title%")
    List<Movie> findByTitle(String title);

}
