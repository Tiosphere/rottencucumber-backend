package tk.rottencucumber.backend.service;

import com.github.slugify.Slugify;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import tk.rottencucumber.backend.model.ActorModel;
import tk.rottencucumber.backend.repository.ActorRepository;
import tk.rottencucumber.backend.util.Slugifier;

import java.io.IOException;

@Service
public class ActorService {

    @Autowired
    private ActorRepository actorRepository;

    public void createActor(String name, MultipartFile image) throws IOException {
        Slugify slugify = Slugifier.getInstance();
        String slug = slugify.slugify(name);
        while (true) {
            if (actorRepository.existsBySlug(slug)) {
                slug = slugify.slugify(slug.concat(RandomString.hashOf(4)));
            } else {
                break;
            }
        }
        if (image == null) {
            actorRepository.save(new ActorModel(name, slug));
            return;
        }
        actorRepository.save(new ActorModel(name, slug, image.getContentType(), image.getBytes()));
    }

    public ActorModel findBySlug(String slug) {
        return actorRepository.findBySlug(slug);
    }

    public void delete(ActorModel entity) {
        actorRepository.delete(entity);
    }

    public Iterable<ActorModel> getAll() {
        return actorRepository.findAll();
    }

    public Iterable<ActorModel> findByName(String name) {
        return actorRepository.findAllByNameContainsIgnoreCase(name);
    }
}

