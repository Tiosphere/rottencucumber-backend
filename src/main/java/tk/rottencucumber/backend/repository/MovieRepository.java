package tk.rottencucumber.backend.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import tk.rottencucumber.backend.model.MovieModel;

@Repository
public interface MovieRepository extends CrudRepository<MovieModel, Long> {

    MovieModel findBySlug(String slug);

    //    @Query(value = "SELECT M FROM MovieModel M WHERE M.genres.slug = ?1 ORDER BY M.release DESC")
    Iterable<MovieModel> findAllByGenres_slugOrderByReleaseDesc(String slug);

    Iterable<MovieModel> findAllByPlatforms_slugOrderByReleaseDesc(String slug);

    Iterable<MovieModel> findMovieModelsByOrderByViewsDesc();

    Iterable<MovieModel> findMovieModelsByOrderByReleaseDesc();
}
