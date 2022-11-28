package tk.rottencucumber.backend.service;

import com.github.slugify.Slugify;
import net.bytebuddy.utility.RandomString;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import tk.rottencucumber.backend.model.DirectorModel;
import tk.rottencucumber.backend.repository.DirectorRepository;
import tk.rottencucumber.backend.util.Slugifier;

import java.io.IOException;

@Service
public class DirectorService {

    private final DirectorRepository repository;

    public DirectorService(DirectorRepository repository) {
        this.repository = repository;
    }

    public void createDirector(String name, MultipartFile image) throws IOException {
        Slugify slugify = Slugifier.getInstance();
        String slug = slugify.slugify(name);
        while (true) {
            if (repository.existsBySlug(slug)) {
                slug = slugify.slugify(slug.concat(RandomString.hashOf(4)));
            } else {
                break;
            }
        }
        if (image == null) {
            repository.save(new DirectorModel(name, slug));
            return;
        }
        repository.save(new DirectorModel(name, slug, image.getContentType(), image.getBytes()));
    }

    public DirectorModel findBySlug(String slug) {
        return repository.findBySlug(slug);
    }

    public void delete(DirectorModel entity) {
        repository.delete(entity);
    }

    public Iterable<DirectorModel> getAll() {
        return repository.findAll();
    }

    public Iterable<DirectorModel> findByName(String name) {
        return repository.findAllByNameContainsIgnoreCase(name);
    }
}

