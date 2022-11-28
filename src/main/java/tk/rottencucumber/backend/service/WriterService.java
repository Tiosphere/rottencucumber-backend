package tk.rottencucumber.backend.service;

import com.github.slugify.Slugify;
import net.bytebuddy.utility.RandomString;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import tk.rottencucumber.backend.model.WriterModel;
import tk.rottencucumber.backend.repository.WriterRepository;
import tk.rottencucumber.backend.util.Slugifier;

import java.io.IOException;

@Service
public class WriterService {

    private final WriterRepository repository;

    public WriterService(WriterRepository repository) {
        this.repository = repository;
    }

    public void createWriter(String name, MultipartFile image) throws IOException {
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
            repository.save(new WriterModel(name, slug));
            return;
        }
        repository.save(new WriterModel(name, slug, image.getContentType(), image.getBytes()));
    }

    public WriterModel findBySlug(String slug) {
        return repository.findBySlug(slug);
    }

    public void delete(WriterModel entity) {
        repository.delete(entity);
    }

    public Iterable<WriterModel> getAll() {
        return repository.findAll();
    }

    public Iterable<WriterModel> findByName(String name) {
        return repository.findAllByNameContainsIgnoreCase(name);
    }
}

