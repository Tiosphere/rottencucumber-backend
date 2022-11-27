package tk.rottencucumber.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.rottencucumber.backend.model.GenreModel;
import tk.rottencucumber.backend.repository.GenreRepository;
import tk.rottencucumber.backend.util.Slugifier;

@Service
public class GenreService {

    @Autowired
    private GenreRepository genreRepository;

    public void createGenre(String name) {
        genreRepository.save(new GenreModel(name, Slugifier.getInstance().slugify(name)));
    }

    public GenreModel findBySlug(String slug) {
        return genreRepository.findBySlug(slug);
    }

    public void delete(GenreModel entity) {
        genreRepository.delete(entity);
    }

    public Iterable<GenreModel> getAll() {
        return genreRepository.findAll();
    }
}

