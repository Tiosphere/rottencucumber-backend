package tk.rottencucumber.backend.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import tk.rottencucumber.backend.model.GenreModel;
import tk.rottencucumber.backend.model.MovieModel;

import java.util.Set;

@Repository
public interface MovieRepository extends CrudRepository<MovieModel, Long> {

    MovieModel findBySlug(String slug);

    Iterable<MovieModel> findAllByGenresIs(Set<GenreModel> genres);

    Iterable<MovieModel> findMovieModelsByOrderByViewsDesc();

    Iterable<MovieModel> findMovieModelsByOrderByReleaseDesc();
}
