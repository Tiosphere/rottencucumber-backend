package tk.rottencucumber.backend.service;

import com.github.slugify.Slugify;
import net.bytebuddy.utility.RandomString;
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
    public void update(GenreModel model, String name) {
        if (!name.equals(model.getName())) {
            Slugify slugify = Slugifier.getInstance();
            String slug = slugify.slugify(name);
            while (true) {
                if (repository.existsBySlug(slug)) {
                    slug = slugify.slugify(slug.concat(RandomString.hashOf(4)));
                } else {
                    break;
                }
            }
            model.setName(name);
            model.setSlug(slug);
        }
        repository.save(model);
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

