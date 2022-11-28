package tk.rottencucumber.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.rottencucumber.backend.model.PlatformModel;
import tk.rottencucumber.backend.repository.PlatformRepository;
import tk.rottencucumber.backend.util.Slugifier;

@Service
public class PlatformService {

    @Autowired
    private PlatformRepository platformRepository;

    public void createPlatform(String name) {
        platformRepository.save(new PlatformModel(name, Slugifier.getInstance().slugify(name)));
    }

    public PlatformModel findBySlug(String slug) {
        return platformRepository.findBySlug(slug);
    }

    public void delete(PlatformModel entity) {
        platformRepository.delete(entity);
    }

    public Iterable<PlatformModel> getAll() {
        return platformRepository.findAll();
    }
}

