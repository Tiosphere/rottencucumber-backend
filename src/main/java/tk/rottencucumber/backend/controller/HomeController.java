package tk.rottencucumber.backend.controller;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import tk.rottencucumber.backend.controller.admin.*;
import tk.rottencucumber.backend.model.*;
import tk.rottencucumber.backend.record.movie.MovieRecord;
import tk.rottencucumber.backend.record.movie.MovieRecordTool;
import tk.rottencucumber.backend.record.person.PersonRecord;
import tk.rottencucumber.backend.record.person.PersonRecordTool;
import tk.rottencucumber.backend.record.response.BoolResponse;
import tk.rottencucumber.backend.record.response.ObjectResponse;
import tk.rottencucumber.backend.record.review.ReviewCreateForm;
import tk.rottencucumber.backend.record.simple.SimpleRecord;
import tk.rottencucumber.backend.record.simple.SimpleRecordTool;
import tk.rottencucumber.backend.record.user.UserRecordTool;
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
    private final UserService userService;
    private final ReviewService reviewService;

    public HomeController(MovieService movieService, GenreService genreService, PlatformService platformService, LanguageService languageService, ActorService actorService, DirectorService directorService, WriterService writerService, UserService userService, ReviewService reviewService) {
        this.movieService = movieService;
        this.genreService = genreService;
        this.platformService = platformService;
        this.languageService = languageService;
        this.actorService = actorService;
        this.directorService = directorService;
        this.writerService = writerService;
        this.userService = userService;
        this.reviewService = reviewService;
    }

    @Cacheable(cacheNames = "movies")
    @GetMapping("/movies")
    public List<MovieRecord> allMovies() {
        Iterable<MovieModel> all = movieService.getAll();
        return MovieRecordTool.createRecordList(all);
    }

    @GetMapping("/movie/{slug}")
    public ObjectResponse movie(@PathVariable String slug) {
        MovieModel model = movieService.findBySlug(slug);
        if (model == null) {
            return new ObjectResponse(false, "Can't find movie with this name", null);
        }
        movieService.updateView(model);
        return new ObjectResponse(true, String.format("Successfully get movie %s", model.getName()), List.of(MovieRecordTool.createRecWithReview(model)));
    }

    @Cacheable(cacheNames = "genres")
    @GetMapping("/genres")
    public List<SimpleRecord> allGenres() {
        return new GenreController(genreService).getAll();
    }

    @GetMapping("/genre/{slug}")
    public ObjectResponse genre(@PathVariable String slug) {
        GenreModel model = genreService.findBySlug(slug);
        if (model == null) {
            return new ObjectResponse(false, "Can't find genre with this name", null);
        }
        return new ObjectResponse(true, String.format("Successfully get genre %s", model.getName()), List.of(SimpleRecordTool.createRecWithMovies(model)));
    }

    @Cacheable(cacheNames = "platform")
    @GetMapping("/platforms")
    public List<SimpleRecord> allPlatforms() {
        return new PlatformController(platformService).getAll();
    }

    @GetMapping("/platform/{slug}")
    public ObjectResponse platform(@PathVariable String slug) {
        PlatformModel model = platformService.findBySlug(slug);
        if (model == null) {
            return new ObjectResponse(false, "Can't find genre with this name", null);
        }
        return new ObjectResponse(true, String.format("Successfully get genre %s", model.getName()), List.of(SimpleRecordTool.createRecWithMovies(model)));
    }

    @Cacheable(cacheNames = "language")
    @GetMapping("/languages")
    public List<SimpleRecord> allLanguages() {
        return new LanguageController(languageService).getAll();
    }

    @GetMapping("/language/{slug}")
    public ObjectResponse language(@PathVariable String slug) {
        LanguageModel model = languageService.findBySlug(slug);
        if (model == null) {
            return new ObjectResponse(false, "Can't find language with this name", null);
        }
        return new ObjectResponse(true, String.format("Successfully get language %s", model.getName()), List.of(SimpleRecordTool.createRecWithMovies(model)));
    }

    @Cacheable("actors")
    @GetMapping("/actors")
    public List<PersonRecord> allActors() {
        return new ActorController(actorService).getAll();
    }

    @GetMapping("/actor/{slug}")
    public ObjectResponse actor(@PathVariable String slug) {
        ActorModel model = actorService.findBySlug(slug);
        if (model == null) {
            return new ObjectResponse(false, "Can't find actor with this name", null);
        }
        return new ObjectResponse(true, String.format("Successfully get actor %s", model.getName()), List.of(PersonRecordTool.createRecWithMovies(model)));
    }

    @Cacheable(cacheNames = "directors")
    @GetMapping("/directors")
    public List<PersonRecord> allDirectors() {
        return new DirectorController(directorService).getAll();
    }

    @GetMapping("/director/{slug}")
    public ObjectResponse director(@PathVariable String slug) {
        DirectorModel model = directorService.findBySlug(slug);
        if (model == null) {
            return new ObjectResponse(false, "Can't find director with this name", null);
        }
        return new ObjectResponse(true, String.format("Successfully get director %s", model.getName()), List.of(PersonRecordTool.createRecWithMovies(model)));
    }

    @Cacheable(cacheNames = "writers")
    @GetMapping("/writers")
    public List<PersonRecord> allWriters() {
        return new WriterController(writerService).getAll();
    }

    @GetMapping("/writer/{slug}")
    public ObjectResponse writer(@PathVariable String slug) {
        WriterModel model = writerService.findBySlug(slug);
        if (model == null) {
            return new ObjectResponse(false, "Can't find writer with this name", null);
        }
        return new ObjectResponse(true, String.format("Successfully get writer %s", model.getName()), List.of(PersonRecordTool.createRecWithMovies(model)));
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/review/{slug}")
    // require movie slug
    public BoolResponse review(@PathVariable String slug, ReviewCreateForm form) {
        MovieModel movie = movieService.findBySlug(slug);
        System.out.println(form.comment());
        if (movie == null) {
            return new BoolResponse(false, "Movie doesn't not exist");
        }
        UserModel user = userService.findByUsername(form.username());
        if (user == null) {
            return new BoolResponse(false, "User doesn't not exist");
        }
        reviewService.createReview(user, movie, form.comment());
        return new BoolResponse(true, "Successfully add new review");
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/user/{slug}")
    public ObjectResponse userProfile(@PathVariable String slug) {
        UserModel userModel = userService.findBySlug(slug);
        if (userModel == null) {
            return new ObjectResponse(false, "User doesn't not exist", null);
        }
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!user.getUsername().equals(userModel.getUsername())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        return new ObjectResponse(true, "Successfully retrieve user", List.of(UserRecordTool.createRecWithFav(userModel)));
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/fav/{slug}")
    //movie slug
    //this function use for both add and remove favourite
    public BoolResponse favorite(@PathVariable String slug) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserModel userModel = userService.findByUsername(user.getUsername());
        if (userModel == null) {
            return new BoolResponse(false, "can't find this user");
        }
        MovieModel movie = movieService.findBySlug(slug);
        if (movie == null) {
            return new BoolResponse(false, "can't find this movie");
        }
        movieService.addFav(movie, userModel);
        return new BoolResponse(true, "Successfully add this movie");
    }
}
