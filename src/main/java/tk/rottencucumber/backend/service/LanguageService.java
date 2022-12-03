package tk.rottencucumber.backend.service;

import com.github.slugify.Slugify;
import net.bytebuddy.utility.RandomString;
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

    public void createLanguage(String name) {
        repository.save(new LanguageModel(name, Slugifier.getInstance().slugify(name)));
    }

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

    public void delete(LanguageModel entity) {
        repository.delete(entity);
    }

    public Iterable<LanguageModel> getAll() {
        return repository.findAll();
    }
}

