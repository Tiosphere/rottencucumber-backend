package tk.rottencucumber.backend.service;

import org.springframework.stereotype.Service;
import tk.rottencucumber.backend.model.MovieModel;
import tk.rottencucumber.backend.repository.MovieRepository;

@Service
public class MovieService {
    private final MovieRepository repository;

    public MovieService(MovieRepository repository) {
        this.repository = repository;
    }

    public MovieModel getBySlug(String slug) {
        return repository.findBySlug(slug);
    }

    public Iterable<MovieModel> getMostViews() {
        return repository.findMovieModelsByOrderByViewsDesc();
    }

    public Iterable<MovieModel> getLatest() {
        return repository.findMovieModelsByOrderByReleaseDesc();
    }
}
