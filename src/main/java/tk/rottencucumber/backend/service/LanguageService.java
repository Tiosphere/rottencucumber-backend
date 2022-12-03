package tk.rottencucumber.backend.service;

import com.github.slugify.Slugify;
import net.bytebuddy.utility.RandomString;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import tk.rottencucumber.backend.model.LanguageModel;
import tk.rottencucumber.backend.repository.LanguageRepository;
import tk.rottencucumber.backend.util.Slugifier;

@Service
public class LanguageService {

    private final LanguageRepository repository;

    public LanguageService(LanguageRepository repository) {
        this.repository = repository;
    }

    @CacheEvict(cacheNames = "language")
    public void createLanguage(String name) {
        repository.save(new LanguageModel(name, Slugifier.getInstance().slugify(name)));
    }

    @CacheEvict(cacheNames = "language")
    public void update(LanguageModel model, String name) {
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

    public LanguageModel findBySlug(String slug) {
        return repository.findBySlug(slug);
    }

    @CacheEvict(cacheNames = "language")
    public void delete(LanguageModel entity) {
        repository.delete(entity);
    }

    @Cacheable(cacheNames = "language")
    public Iterable<LanguageModel> getAll() {
        return repository.findAll();
    }
}

