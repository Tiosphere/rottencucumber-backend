package tk.rottencucumber.backend.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import tk.rottencucumber.backend.model.GenreModel;
import tk.rottencucumber.backend.model.MovieModel;
import tk.rottencucumber.backend.model.PlatformModel;
import tk.rottencucumber.backend.record.movie.MovieRecord;
import tk.rottencucumber.backend.record.movie.MovieRecordBuilder;
import tk.rottencucumber.backend.record.response.ObjectResponse;
import tk.rottencucumber.backend.record.simple.SimpleRecord;
import tk.rottencucumber.backend.record.simple.SimpleRecordBuilder;
import tk.rottencucumber.backend.service.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class HomeController {
    private final MovieService movieService;
    private final GenreService genreService;
    private final PlatformService platformService;
    private final ActorService actorService;
    private final WriterService writerService;

    public HomeController(MovieService movieService, GenreService genreService, PlatformService platformService, ActorService actorService, WriterService writerService) {
        this.movieService = movieService;
        this.genreService = genreService;
        this.platformService = platformService;
        this.actorService = actorService;
        this.writerService = writerService;
    }

    @GetMapping("/movies")
    public List<MovieRecord> allMovie() {
        Iterable<MovieModel> all = movieService.getAll();
        return getMovieRecords(all);
    }

    @GetMapping("/movie/{slug}")
    public ObjectResponse movie(@PathVariable String slug) {
        MovieModel model = movieService.getBySlug(slug);
        if (model == null) {
            return new ObjectResponse(false, "Can't find movie with this name", null);
        }
        return new ObjectResponse(true, String.format("Successfully get movie %s", model.getName()), List.of(MovieRecordBuilder.create(model)));
    }

    @GetMapping("/genres")
    public List<SimpleRecord> allGenres() {
        Iterable<GenreModel> all = genreService.getAll();
        List<SimpleRecord> list = new ArrayList<>();
        for (GenreModel model : all) {
            list.add(SimpleRecordBuilder.create(model));
        }
        return list;
    }

    @GetMapping("/genre/{slug}")
    public List<MovieRecord> genre(@PathVariable String slug) {
        Iterable<MovieModel> all = movieService.getByGenre(slug);
        return getMovieRecords(all);
    }

    @GetMapping("/platforms")
    public List<SimpleRecord> allPlatform() {
        Iterable<PlatformModel> all = platformService.getAll();
        List<SimpleRecord> list = new ArrayList<>();
        for (PlatformModel model : all) {
            list.add(SimpleRecordBuilder.create(model));
        }
        return list;
    }

    @GetMapping("/platform/{slug}")
    public List<MovieRecord> platform(@PathVariable String slug) {
        Iterable<MovieModel> all = movieService.getByPlatform(slug);
        return getMovieRecords(all);
    }

//    private record ActorResponse(ActorRecord actor, List<Record> movies) {}
//    @GetMapping("/actor/{slug}")
//    public ActorResponse actor(@PathVariable String slug) {
//        ActorModel actor = actorService.findBySlug(slug);
//    }

//    private record WriterResponse(WriterRecord actor, List<Record> movies) {}
//    @GetMapping("/writer/{slug}")
//    public WriterResponse writer(@PathVariable String slug) {
//        WriterModel writer = writerService.findBySlug(slug);
//        return new WriterResponse(new WriterRecord(writer.getName(), writer.getSlug(), Base64Encoder.encode(writer.getImage()), writer.getType())); getMovieRecords(writer.getMovies()));
//    }

    private List<MovieRecord> getMovieRecords(Iterable<MovieModel> all) {
        List<MovieRecord> list = new ArrayList<>();
        for (MovieModel model : all) {
            list.add(MovieRecordBuilder.create(model));
        }
        return list;
    }
}
