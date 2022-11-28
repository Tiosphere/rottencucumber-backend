package tk.rottencucumber.backend.service;

import org.springframework.stereotype.Service;
import tk.rottencucumber.backend.model.GenreModel;
import tk.rottencucumber.backend.repository.GenreRepository;
import tk.rottencucumber.backend.util.Slugifier;

@Service
public class GenreService {

    private final GenreRepository repository;

    public GenreService(GenreRepository repository) {
        this.repository = repository;
    }

    public void createGenre(String name) {
        repository.save(new GenreModel(name, Slugifier.getInstance().slugify(name)));
    }

    public GenreModel findBySlug(String slug) {
        return repository.findBySlug(slug);
    }

    public void delete(GenreModel entity) {
        repository.delete(entity);
    }

    public Iterable<GenreModel> getAll() {
        return repository.findAll();
    }
}

