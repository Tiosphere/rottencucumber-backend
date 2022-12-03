package tk.rottencucumber.backend.service;

import com.github.slugify.Slugify;
import net.bytebuddy.utility.RandomString;
import org.springframework.stereotype.Service;
import tk.rottencucumber.backend.model.*;
import tk.rottencucumber.backend.record.movie.MovieCreateForm;
import tk.rottencucumber.backend.record.response.CodeResponse;
import tk.rottencucumber.backend.repository.*;
import tk.rottencucumber.backend.util.Slugifier;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

@Service
public class MovieService {
    private final MovieRepository repository;
    private final ActorRepository actorRepository;
    private final DirectorRepository directorRepository;
    private final WriterRepository writerRepository;
    private final LanguageRepository languageRepository;
    private final GenreRepository genreRepository;
    private final PlatformRepository platformRepository;

    public MovieService(MovieRepository repository, ActorRepository actorRepository, DirectorRepository directorRepository, WriterRepository writerRepository, LanguageRepository languageRepository, GenreRepository genreRepository, PlatformRepository platformRepository) {
        this.repository = repository;
        this.actorRepository = actorRepository;
        this.directorRepository = directorRepository;
        this.writerRepository = writerRepository;
        this.languageRepository = languageRepository;
        this.genreRepository = genreRepository;
        this.platformRepository = platformRepository;
    }

    public MovieModel findBySlug(String slug) {
        return repository.findBySlug(slug);
    }

    public void delete(MovieModel entity) {
        repository.delete(entity);
    }

    public Iterable<MovieModel> getAll() {
        return repository.findAll();
    }

    public CodeResponse createMovie(MovieCreateForm form) {
        Set<ActorModel> actors = new HashSet<>();
        Set<DirectorModel> directors = new HashSet<>();
        Set<WriterModel> writers = new HashSet<>();
        Set<PlatformModel> platform = new HashSet<>();
        Set<GenreModel> genres = new HashSet<>();
        LanguageModel language = languageRepository.findById(form.language()).orElse(null);
        if (language == null) {
            return new CodeResponse(1, String.format("Can't find language with this id: %d", form.language()));
        }
        for (Long id : form.actors()) {
            ActorModel model = actorRepository.findById(id).orElse(null);
            if (model == null) {
                return new CodeResponse(2, String.format("Can't find actor with this id: %d", id));
            }
            actors.add(model);
        }
        for (Long id : form.directors()) {
            DirectorModel model = directorRepository.findById(id).orElse(null);
            if (model == null) {
                return new CodeResponse(3, String.format("Can't find director with this id: %d", id));
            }
            directors.add(model);
        }
        for (Long id : form.writers()) {
            WriterModel model = writerRepository.findById(id).orElse(null);
            if (model == null) {
                return new CodeResponse(4, String.format("Can't find writer with this id: %d", id));
            }
            writers.add(model);
        }
        for (Long id : form.platform()) {
            PlatformModel model = platformRepository.findById(id).orElse(null);
            if (model == null) {
                return new CodeResponse(5, String.format("Can't find platform with this id: %d", id));
            }
            platform.add(model);
        }
        for (Long id : form.genres()) {
            GenreModel model = genreRepository.findById(id).orElse(null);
            if (model == null) {
                return new CodeResponse(6, String.format("Can't find genre with this id: %d", id));
            }
            genres.add(model);
        }

        String name = form.name();
        Slugify slugify = Slugifier.getInstance();
        String slug = slugify.slugify(name);
        while (true) {
            if (repository.existsBySlug(slug)) {
                slug = slugify.slugify(slug.concat(RandomString.hashOf(4)));
            } else {
                break;
            }
        }
        byte[] image = null;
        String type = null;
        if (!form.image().isEmpty()) {
            try {
                image = form.image().getBytes();
            } catch (IOException e) {
                return new CodeResponse(7, "Can't process the image please try again");
            }
            type = form.image().getContentType();
        }
        repository.save(new MovieModel(
                name,
                slug,
                form.preview(),
                form.release(),
                language,
                actors,
                writers,
                directors,
                genres,
                platform,
                image,
                type,
                form.summary()
        ));
        return new CodeResponse(0, String.format("Successfully created movie %s", form.name()));
    }

    public CodeResponse update(MovieModel entity, MovieCreateForm form) {
        Set<ActorModel> actors = new HashSet<>();
        Set<DirectorModel> directors = new HashSet<>();
        Set<WriterModel> writers = new HashSet<>();
        Set<PlatformModel> platform = new HashSet<>();
        Set<GenreModel> genres = new HashSet<>();
        LanguageModel language = languageRepository.findById(form.language()).orElse(null);
        if (language == null) {
            return new CodeResponse(1, String.format("Can't find language with this id: %d", form.language()));
        }
        for (Long id : form.actors()) {
            ActorModel model = actorRepository.findById(id).orElse(null);
            if (model == null) {
                return new CodeResponse(2, String.format("Can't find actor with this id: %d", id));
            }
            actors.add(model);
        }
        for (Long id : form.directors()) {
            DirectorModel model = directorRepository.findById(id).orElse(null);
            if (model == null) {
                return new CodeResponse(3, String.format("Can't find director with this id: %d", id));
            }
            directors.add(model);
        }
        for (Long id : form.writers()) {
            WriterModel model = writerRepository.findById(id).orElse(null);
            if (model == null) {
                return new CodeResponse(4, String.format("Can't find writer with this id: %d", id));
            }
            writers.add(model);
        }
        for (Long id : form.platform()) {
            PlatformModel model = platformRepository.findById(id).orElse(null);
            if (model == null) {
                return new CodeResponse(5, String.format("Can't find platform with this id: %d", id));
            }
            platform.add(model);
        }
        for (Long id : form.genres()) {
            GenreModel model = genreRepository.findById(id).orElse(null);
            if (model == null) {
                return new CodeResponse(6, String.format("Can't find genre with this id: %d", id));
            }
            genres.add(model);
        }
        String name = form.name();
        Slugify slugify = Slugifier.getInstance();
        String slug = slugify.slugify(name);
        if (!entity.getSlug().equals(slug)) {
            while (true) {
                if (repository.existsBySlug(slug)) {
                    slug = slugify.slugify(slug.concat(RandomString.hashOf(4)));
                } else {
                    break;
                }
            }
            entity.setSlug(slug);
        }
        entity.setName(name);
        byte[] image = null;
        String type = null;
        if (!form.image().isEmpty()) {
            try {
                image = form.image().getBytes();
            } catch (IOException e) {
                return new CodeResponse(7, "Can't process the image please try again");
            }
            type = form.image().getContentType();
        }
        entity.setImage(image);
        entity.setType(type);
        entity.setPreview(form.preview());
        entity.setRelease(form.release());
        entity.setLanguage(language);
        entity.setActors(actors);
        entity.setDirectors(directors);
        entity.setWriters(writers);
        entity.setGenres(genres);
        entity.setPlatforms(platform);
        entity.setSummary(form.summary());
        repository.save(entity);
        return new CodeResponse(0, String.format("Successfully created movie %s", form.name()));
    }
}
