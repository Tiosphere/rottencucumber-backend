package tk.rottencucumber.backend.service;

import com.github.slugify.Slugify;
import net.bytebuddy.utility.RandomString;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import tk.rottencucumber.backend.model.ActorModel;
import tk.rottencucumber.backend.record.person.PersonCreateForm;
import tk.rottencucumber.backend.repository.ActorRepository;
import tk.rottencucumber.backend.util.Slugifier;

import java.io.IOException;
import java.time.LocalDate;

@Service
public class ActorService {
    private final ActorRepository repository;

    public ActorService(ActorRepository repository) {
        this.repository = repository;
    }

    @CacheEvict(cacheNames = "actors")
    public void createActor(PersonCreateForm form) throws IOException {
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
        if (form.image() != null) {
            image = form.image().getBytes();
            type = form.image().getContentType();
        }
        repository.save(new ActorModel(name, slug, type, image, form.birthPlace(), LocalDate.of(form.year(), form.month(), form.day()), form.description()));
    }

    @CacheEvict(cacheNames = "actors")
    public void update(ActorModel model, PersonCreateForm form) throws IOException {
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
        if (image != null) {
            model.setImage(image.getBytes());
            model.setType(image.getContentType());
        }
        model.setBirthday(LocalDate.of(form.year(), form.month(), form.day()));
        System.out.println(model.getBirthPlace() + form.birthPlace());
        model.setBirthPlace(form.birthPlace());
        model.setDescription(form.description());
        repository.save(model);
    }

    public ActorModel findBySlug(String slug) {
        return repository.findBySlug(slug);
    }

    @CacheEvict(cacheNames = "actors")
    public void delete(ActorModel entity) {
        repository.delete(entity);
    }

    public Iterable<ActorModel> getAll() {
        return repository.findAll();
    }
}

