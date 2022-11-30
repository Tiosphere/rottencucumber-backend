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

    public MovieModel findBySlug(String slug) {
        return repository.findBySlug(slug);
    }

    public Iterable<MovieModel> getByGenre(String slug) {
        return repository.findAllByGenres_slugOrderByReleaseDesc(slug);
    }

    public Iterable<MovieModel> getByPlatform(String slug) {
        return repository.findAllByPlatforms_slugOrderByReleaseDesc(slug);
    }

    public void delete(MovieModel entity) {
        repository.delete(entity);
    }

    public Iterable<MovieModel> getAll() {
        return repository.findAll();
    }

    public MovieModel getBySlug(String slug) {
        return repository.findBySlug(slug);
    }
}
