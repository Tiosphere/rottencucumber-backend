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
    public void delete(MovieModel entity) {
        repository.delete(entity);
    }
    public Iterable<MovieModel> getAll() {
        return repository.findAll();
    }
}
