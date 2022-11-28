package tk.rottencucumber.backend.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import tk.rottencucumber.backend.model.GenreModel;
import tk.rottencucumber.backend.model.MovieModel;
import tk.rottencucumber.backend.model.PlatformModel;
import tk.rottencucumber.backend.record.main.GenreRecord;
import tk.rottencucumber.backend.record.main.MovieRecord;
import tk.rottencucumber.backend.service.GenreService;
import tk.rottencucumber.backend.service.MovieService;
import tk.rottencucumber.backend.service.PlatformService;
import tk.rottencucumber.backend.util.Base64Encoder;

import java.util.ArrayList;
import java.util.List;

@RestController
public class HomeController {

    private final MovieService movieService;

    private final GenreService genreService;

    private final PlatformService platformService;

    public HomeController(MovieService movieService, GenreService genreService, PlatformService platformService) {
        this.movieService = movieService;
        this.genreService = genreService;
        this.platformService = platformService;
    }

    @GetMapping("/home")
    public HomeResponse Home() {
        Iterable<MovieModel> latest = movieService.getLatest();
        Iterable<MovieModel> popular = movieService.getMostViews();
        return new HomeResponse(getMovieRecords(popular, 20), getMovieRecords(latest, 20));
    }

    @GetMapping("/latest")
    public List<Record> latest() {
        Iterable<MovieModel> all = movieService.getLatest();
        return getMovieRecords(all);
    }

    @GetMapping("/popular")
    public List<Record> popular() {
        Iterable<MovieModel> all = movieService.getMostViews();
        return getMovieRecords(all);
    }

    @GetMapping("/genres")
    public List<Record> allGenres() {
        Iterable<GenreModel> all = genreService.getAll();
        List<Record> list = new ArrayList<>();
        for (GenreModel model : all) {
            list.add(new GenreRecord(model.getName(), model.getSlug()));
        }
        return list;
    }

    @GetMapping("/genres/{slug}")
    public List<Record> genre(@PathVariable String slug) {
        Iterable<MovieModel> all = movieService.getByGenre(slug);
        return getMovieRecords(all);
    }

    @GetMapping("/platform")
    public List<Record> allPlatform() {
        Iterable<PlatformModel> all = platformService.getAll();
        List<Record> list = new ArrayList<>();
        for (PlatformModel model : all) {
            list.add(new GenreRecord(model.getName(), model.getSlug()));
        }
        return list;
    }

    @GetMapping("/platform/{slug}")
    public List<Record> platform(@PathVariable String slug) {
        Iterable<MovieModel> all = movieService.getByPlatform(slug);
        return getMovieRecords(all);
    }

    private List<Record> getMovieRecords(Iterable<MovieModel> movieModelIterable) {
        return getMovieRecords(movieModelIterable, 0);
    }

    private List<Record> getMovieRecords(Iterable<MovieModel> movieModelIterable, Integer size) {
        List<Record> records = new ArrayList<>();
        if (size == 0) {
            for (MovieModel model : movieModelIterable) {
                movieListAdder(records, model);
            }
        } else {
            for (MovieModel model : movieModelIterable) {
                movieListAdder(records, model);
                if (records.size() == size) {
                    break;
                }
            }
        }
        return records;
    }

    private void movieListAdder(List<Record> records, MovieModel model) {
        if (model.getImage() == null) {
            records.add(new MovieRecord(
                    model.getName(),
                    model.getSlug(),
                    null,
                    null,
                    model.getPreview(),
                    model.getSummary(),
                    model.getRelease()
            ));
        } else {
            records.add(new MovieRecord(
                    model.getName(),
                    model.getSlug(),
                    Base64Encoder.encode(model.getImage()),
                    model.getType(),
                    model.getPreview(),
                    model.getSummary(),
                    model.getRelease()
            ));
        }
    }

    private record HomeResponse(List<Record> popular, List<Record> latest) {
    }

}
