package tk.rottencucumber.backend.service;

import com.github.slugify.Slugify;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import tk.rottencucumber.backend.model.DirectorModel;
import tk.rottencucumber.backend.repository.DirectorRepository;
import tk.rottencucumber.backend.util.Slugifier;

import java.io.IOException;

@Service
public class DirectorService {

    @Autowired
    private DirectorRepository directorRepository;

    public void createDirector(String name, MultipartFile image) throws IOException {
        Slugify slugify = Slugifier.getInstance();
        String slug = slugify.slugify(name);
        while (true) {
            if (directorRepository.existsBySlug(slug)) {
                slug = slugify.slugify(slug.concat(RandomString.hashOf(4)));
            } else {
                break;
            }
        }
        if (image == null) {
            directorRepository.save(new DirectorModel(name, slug));
            return;
        }
        directorRepository.save(new DirectorModel(name, slug, image.getContentType(), image.getBytes()));
    }

    public DirectorModel findBySlug(String slug) {
        return directorRepository.findBySlug(slug);
    }

    public void delete(DirectorModel entity) {
        directorRepository.delete(entity);
    }

    public Iterable<DirectorModel> getAll() {
        return directorRepository.findAll();
    }

    public Iterable<DirectorModel> findByName(String name) {
        return directorRepository.findAllByNameContainsIgnoreCase(name);
    }
}

