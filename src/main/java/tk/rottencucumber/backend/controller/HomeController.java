package tk.rottencucumber.backend.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import tk.rottencucumber.backend.model.GenreModel;
import tk.rottencucumber.backend.model.MovieModel;
import tk.rottencucumber.backend.record.main.GenreRecord;
import tk.rottencucumber.backend.record.main.MovieRecord;
import tk.rottencucumber.backend.service.GenreService;
import tk.rottencucumber.backend.service.MovieService;
import tk.rottencucumber.backend.util.Base64Encoder;

import java.util.ArrayList;
import java.util.List;

@RestController
public class HomeController {

    private final MovieService movieService;

    private final GenreService genreService;

    public HomeController(MovieService movieService, GenreService genreService) {
        this.movieService = movieService;
        this.genreService = genreService;
    }

    @GetMapping("/home")
    public HomeResponse Home() {
        return new HomeResponse(getMostViews(20), getLatestMovies(20));
    }

    @GetMapping("/latest")
    public List<Record> latest() {
        return getLatestMovies(0);
    }

    @GetMapping("/popular")
    public List<Record> popular() {
        return getMostViews(0);
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

    private List<Record> getLatestMovies(Integer size) {
        Iterable<MovieModel> latest = movieService.getLatest();
        return getMovieRecords(latest, size);
    }

    private List<Record> getMostViews(Integer size) {
        Iterable<MovieModel> popular = movieService.getMostViews();
        return getMovieRecords(popular, size);
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
