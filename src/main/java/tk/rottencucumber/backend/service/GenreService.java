package tk.rottencucumber.backend.service;

import com.github.slugify.Slugify;
import net.bytebuddy.utility.RandomString;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
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

    @CacheEvict(cacheNames = "genres")
    public void createGenre(String name) {
        repository.save(new GenreModel(name, Slugifier.getInstance().slugify(name)));
    }

    @CacheEvict(cacheNames = "genres")
    public void update(GenreModel model, String name) {
        Slugify slugify = Slugifier.getInstance();
        String slug = slugify.slugify(name);
        if (!slug.equals(model.getSlug())) {
            while (true) {
                if (repository.existsBySlug(slug)) {
                    slug = slugify.slugify(slug.concat(RandomString.hashOf(4)));
                } else {
                    break;
                }
            }
            model.setSlug(slug);
        }
        model.setName(name);
        repository.save(model);
    }
    public GenreModel findBySlug(String slug) {
        return repository.findBySlug(slug);
    }

    @CacheEvict(cacheNames = "genres")
    public void delete(GenreModel entity) {
        repository.delete(entity);
    }

    @Cacheable(cacheNames = "genres")
    public Iterable<GenreModel> getAll() {
        return repository.findAll();
    }
}

