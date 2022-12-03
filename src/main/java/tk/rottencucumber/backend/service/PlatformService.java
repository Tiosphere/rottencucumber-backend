package tk.rottencucumber.backend.service;

import com.github.slugify.Slugify;
import net.bytebuddy.utility.RandomString;
import org.springframework.stereotype.Service;
import tk.rottencucumber.backend.model.PlatformModel;
import tk.rottencucumber.backend.repository.PlatformRepository;
import tk.rottencucumber.backend.util.Slugifier;

@Service
public class PlatformService {

    private final PlatformRepository repository;

    public PlatformService(PlatformRepository repository) {
        this.repository = repository;
    }

    public void createPlatform(String name) {
        repository.save(new PlatformModel(name, Slugifier.getInstance().slugify(name)));
    }

    public void update(PlatformModel model, String name) {
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

    public PlatformModel findBySlug(String slug) {
        return repository.findBySlug(slug);
    }

    public void delete(PlatformModel entity) {
        repository.delete(entity);
    }

    public Iterable<PlatformModel> getAll() {
        return repository.findAll();
    }
}

