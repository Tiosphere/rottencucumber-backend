package tk.rottencucumber.backend.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import tk.rottencucumber.backend.model.*;
import tk.rottencucumber.backend.record.movie.MovieRecord;
import tk.rottencucumber.backend.record.movie.MovieRecordTool;
import tk.rottencucumber.backend.record.movie.MovieRecordWithReviewBuilder;
import tk.rottencucumber.backend.record.person.PersonRecord;
import tk.rottencucumber.backend.record.person.PersonRecordTool;
import tk.rottencucumber.backend.record.person.PersonRecordWithMoviesBuilder;
import tk.rottencucumber.backend.record.response.ObjectResponse;
import tk.rottencucumber.backend.record.simple.SimpleRecord;
import tk.rottencucumber.backend.record.simple.SimpleRecordTool;
import tk.rottencucumber.backend.record.simple.SimpleRecordWithMoviesBuilder;
import tk.rottencucumber.backend.service.*;

import java.util.List;

@RestController
public class HomeController {
    private final MovieService movieService;
    private final GenreService genreService;
    private final PlatformService platformService;
    private final LanguageService languageService;
    private final ActorService actorService;
    private final DirectorService directorService;
    private final WriterService writerService;


    public HomeController(MovieService movieService, GenreService genreService, PlatformService platformService, LanguageService languageService, ActorService actorService, DirectorService directorService, WriterService writerService) {
        this.movieService = movieService;
        this.genreService = genreService;
        this.platformService = platformService;
        this.languageService = languageService;
        this.actorService = actorService;
        this.directorService = directorService;
        this.writerService = writerService;
    }

    @GetMapping("/movies")
    public List<MovieRecord> allMovies() {
        Iterable<MovieModel> all = movieService.getAll();
        return MovieRecordTool.getMovieRecordList(all);
    }

    @GetMapping("/movie/{slug}")
    public ObjectResponse movie(@PathVariable String slug) {
        MovieModel model = movieService.findBySlug(slug);
        if (model == null) {
            return new ObjectResponse(false, "Can't find movie with this name", null);
        }
        return new ObjectResponse(true, String.format("Successfully get movie %s", model.getName()), List.of(MovieRecordWithReviewBuilder.create(model)));
    }

    @GetMapping("/genres")
    public List<SimpleRecord> allGenres() {
        Iterable<GenreModel> all = genreService.getAll();
        return SimpleRecordTool.createRecordsWithGen(all);
    }

    @GetMapping("/genre/{slug}")
    public ObjectResponse genre(@PathVariable String slug) {
        GenreModel model = genreService.findBySlug(slug);
        if (model == null) {
            return new ObjectResponse(false, "Can't find genre with this name", null);
        }
        return new ObjectResponse(true, String.format("Successfully get genre %s", model.getName()), List.of(SimpleRecordWithMoviesBuilder.create(model)));
    }

    @GetMapping("/platforms")
    public List<SimpleRecord> allPlatforms() {
        Iterable<PlatformModel> all = platformService.getAll();
        return SimpleRecordTool.createRecordsWithPlat(all);
    }

    @GetMapping("/platform/{slug}")
    public ObjectResponse platform(@PathVariable String slug) {
        PlatformModel model = platformService.findBySlug(slug);
        if (model == null) {
            return new ObjectResponse(false, "Can't find genre with this name", null);
        }
        return new ObjectResponse(true, String.format("Successfully get genre %s", model.getName()), List.of(SimpleRecordWithMoviesBuilder.create(model)));
    }

    @GetMapping("/languages")
    public List<SimpleRecord> allLanguages() {
        Iterable<LanguageModel> all = languageService.getAll();
        return SimpleRecordTool.createRecordsWithLang(all);
    }

    @GetMapping("/language/{slug}")
    public ObjectResponse language(@PathVariable String slug) {
        LanguageModel model = languageService.findBySlug(slug);
        if (model == null) {
            return new ObjectResponse(false, "Can't find language with this name", null);
        }
        return new ObjectResponse(true, String.format("Successfully get language %s", model.getName()), List.of(SimpleRecordWithMoviesBuilder.create(model)));
    }

    @GetMapping("/actors")
    public List<PersonRecord> allActors() {
        Iterable<ActorModel> all = actorService.getAll();
        return PersonRecordTool.createRecordsWithAct(all);
    }

    @GetMapping("/actor/{slug}")
    public ObjectResponse actor(@PathVariable String slug) {
        ActorModel model = actorService.findBySlug(slug);
        if (model == null) {
            return new ObjectResponse(false, "Can't find actor with this name", null);
        }
        return new ObjectResponse(true, String.format("Successfully get actor %s", model.getName()), List.of(PersonRecordWithMoviesBuilder.create(model)));
    }

    @GetMapping("/directors")
    public List<PersonRecord> allDirectors() {
        Iterable<DirectorModel> all = directorService.getAll();
        return PersonRecordTool.createRecordsWithDir(all);
    }

    @GetMapping("/director/{slug}")
    public ObjectResponse director(@PathVariable String slug) {
        DirectorModel model = directorService.findBySlug(slug);
        if (model == null) {
            return new ObjectResponse(false, "Can't find director with this name", null);
        }
        return new ObjectResponse(true, String.format("Successfully get director %s", model.getName()), List.of(PersonRecordWithMoviesBuilder.create(model)));
    }

    @GetMapping("/writers")
    public List<PersonRecord> allWriters() {
        Iterable<WriterModel> all = writerService.getAll();
        return PersonRecordTool.createRecordsWithWri(all);
    }

    @GetMapping("/writer/{slug}")
    public ObjectResponse writer(@PathVariable String slug) {
        WriterModel model = writerService.findBySlug(slug);
        if (model == null) {
            return new ObjectResponse(false, "Can't find writer with this name", null);
        }
        return new ObjectResponse(true, String.format("Successfully get writer %s", model.getName()), List.of(PersonRecordWithMoviesBuilder.create(model)));
    }
}
