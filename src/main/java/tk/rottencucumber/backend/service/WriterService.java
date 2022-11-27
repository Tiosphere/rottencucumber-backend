package tk.rottencucumber.backend.service;

import com.github.slugify.Slugify;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import tk.rottencucumber.backend.model.WriterModel;
import tk.rottencucumber.backend.repository.WriterRepository;
import tk.rottencucumber.backend.util.Slugifier;

import java.io.IOException;

@Service
public class WriterService {

    @Autowired
    private WriterRepository writerRepository;

    public void createWriter(String name, MultipartFile image) throws IOException {
        Slugify slugify = Slugifier.getInstance();
        String slug = slugify.slugify(name);
        while (true) {
            if (writerRepository.existsBySlug(slug)) {
                slug = slugify.slugify(slug.concat(RandomString.hashOf(4)));
            } else {
                break;
            }
        }
        if (image == null) {
            writerRepository.save(new WriterModel(name, slug));
            return;
        }
        writerRepository.save(new WriterModel(name, slug, image.getContentType(), image.getBytes()));
    }

    public WriterModel findBySlug(String slug) {
        return writerRepository.findBySlug(slug);
    }

    public void delete(WriterModel entity) {
        writerRepository.delete(entity);
    }

    public Iterable<WriterModel> getAll() {
        return writerRepository.findAll();
    }

    public Iterable<WriterModel> findByName(String name) {
        return writerRepository.findAllByNameContainsIgnoreCase(name);
    }
}

