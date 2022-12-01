package tk.rottencucumber.backend.controller.admin;

import org.springframework.web.bind.annotation.*;
import tk.rottencucumber.backend.model.MovieModel;
import tk.rottencucumber.backend.record.movie.MovieCreateForm;
import tk.rottencucumber.backend.record.movie.MovieRecord;
import tk.rottencucumber.backend.record.movie.MovieRecordBuilder;
import tk.rottencucumber.backend.record.movie.MovieRecordTool;
import tk.rottencucumber.backend.record.response.BoolResponse;
import tk.rottencucumber.backend.record.response.CodeResponse;
import tk.rottencucumber.backend.record.response.ObjectResponse;
import tk.rottencucumber.backend.service.MovieService;

import java.util.List;

@RestController
@RequestMapping("/admin/movie")
public class MovieController {
    private final MovieService service;

    public MovieController(MovieService service) {
        this.service = service;
    }

    @GetMapping("/get/all")
    public List<MovieRecord> getAll() {
        Iterable<MovieModel> all = service.getAll();
        return MovieRecordTool.getMovieRecordList(all);
    }

    @PostMapping("/delete/{slug}")
    public BoolResponse delete(@PathVariable String slug) {
        MovieModel model = service.findBySlug(slug);
        if (model == null) {
            return new BoolResponse(false, "Can't find movie with this name");
        }
        service.delete(model);
        return new BoolResponse(true, String.format("Successfully deleted movie %s", model.getName()));
    }

    @PostMapping("/create")
    public CodeResponse create(MovieCreateForm form) {
        return service.createMovie(form);
    }

    @PostMapping("/update/{slug}")
    public CodeResponse update(@PathVariable String slug, MovieCreateForm form) {
        MovieModel model = service.findBySlug(slug);
        if (model == null) {
            return new CodeResponse(8, "Can't find movie with this name");
        }
        return service.update(model, form);
    }

    @GetMapping("/get/{slug}")
    public ObjectResponse get(@PathVariable String slug) {
        MovieModel model = service.findBySlug(slug);
        if (model == null) {
            return new ObjectResponse(false, "Can't find movie with this name", null);
        }
        return new ObjectResponse(true, String.format("Successfully get movie %s", model.getName()), List.of(MovieRecordBuilder.create(model)));
    }
}
