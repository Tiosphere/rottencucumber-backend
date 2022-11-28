package tk.rottencucumber.backend.service;

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

    public void createGenre(String name) {
        repository.save(new LanguageModel(name, Slugifier.getInstance().slugify(name)));
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

