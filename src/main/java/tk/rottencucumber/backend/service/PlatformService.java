package tk.rottencucumber.backend.service;

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

