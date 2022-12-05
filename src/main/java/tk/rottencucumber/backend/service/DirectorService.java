package tk.rottencucumber.backend.service;

import com.github.slugify.Slugify;
import net.bytebuddy.utility.RandomString;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import tk.rottencucumber.backend.model.DirectorModel;
import tk.rottencucumber.backend.record.person.PersonCreateForm;
import tk.rottencucumber.backend.repository.DirectorRepository;
import tk.rottencucumber.backend.util.Slugifier;

import java.io.IOException;
import java.time.LocalDate;

@Service
public class DirectorService {

    private final DirectorRepository repository;

    public DirectorService(DirectorRepository repository) {
        this.repository = repository;
    }

    @CacheEvict(cacheNames = "directors")
    public void createDirector(PersonCreateForm form) throws IOException {
        String name = form.name();
        byte[] image = null;
        String type = null;
        Slugify slugify = Slugifier.getInstance();
        String slug = slugify.slugify(name);
        while (true) {
            if (repository.existsBySlug(slug)) {
                slug = slugify.slugify(slug.concat(RandomString.hashOf(4)));
            } else {
                break;
            }
        }
        if (!form.image().isEmpty()) {
            image = form.image().getBytes();
            type = form.image().getContentType();
        }
        repository.save(new DirectorModel(name, slug, type, image, form.birthPlace(), LocalDate.of(form.year(), form.month(), form.day()), form.description()));
    }

    @CacheEvict(cacheNames = "directors")
    public void update(DirectorModel model, PersonCreateForm form) throws IOException {
        String name = form.name();
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
        MultipartFile image = form.image();
        if (!image.isEmpty()) {
            model.setImage(image.getBytes());
            model.setType(image.getContentType());
        }
        model.setBirthday(LocalDate.of(form.year(), form.month(), form.day()));
        model.setBirthPlace(form.birthPlace());
        model.setDescription(form.description());
        repository.save(model);
    }

    public DirectorModel findBySlug(String slug) {
        return repository.findBySlug(slug);
    }

    @CacheEvict(cacheNames = "directors")
    public void delete(DirectorModel entity) {
        repository.delete(entity);
    }

    public Iterable<DirectorModel> getAll() {
        return repository.findAll();
    }
}

