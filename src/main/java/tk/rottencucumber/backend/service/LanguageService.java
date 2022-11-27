package tk.rottencucumber.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.rottencucumber.backend.model.LanguageModel;
import tk.rottencucumber.backend.repository.LanguageRepository;
import tk.rottencucumber.backend.util.Slugifier;

@Service
public class LanguageService {

    @Autowired
    private LanguageRepository languageRepository;

    public void createGenre(String name) {
        languageRepository.save(new LanguageModel(name, Slugifier.getInstance().slugify(name)));
    }

    public LanguageModel findBySlug(String slug) {
        return languageRepository.findBySlug(slug);
    }

    public void delete(LanguageModel entity) {
        languageRepository.delete(entity);
    }

    public Iterable<LanguageModel> getAll() {
        return languageRepository.findAll();
    }
}

