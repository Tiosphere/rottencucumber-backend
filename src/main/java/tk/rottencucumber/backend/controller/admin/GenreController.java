package tk.rottencucumber.backend.controller.admin;

import org.springframework.web.bind.annotation.*;
import tk.rottencucumber.backend.model.GenreModel;
import tk.rottencucumber.backend.record.SimpleRecord;
import tk.rottencucumber.backend.record.category.CategoryCreateForm;
import tk.rottencucumber.backend.record.response.BoolResponse;
import tk.rottencucumber.backend.record.response.ObjectResponse;
import tk.rottencucumber.backend.service.GenreService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/genre")
public class GenreController {

    private final GenreService genreService;

    public GenreController(GenreService genreService) {
        this.genreService = genreService;
    }

    @GetMapping("/get/all")
    public ObjectResponse getAll() {
        Iterable<GenreModel> entities = genreService.getAll();
        List<Record> list = new ArrayList<>();
        for (GenreModel model : entities) {
            list.add(new SimpleRecord(model.getName(), model.getSlug()));
        }
        return new ObjectResponse(true, "Successfully retrieve all genres ", list);
    }

    @PostMapping("/delete/{slug}")
    public BoolResponse delete(@PathVariable String slug) {
        GenreModel model = genreService.findBySlug(slug);
        if (model == null) {
            return new BoolResponse(false, "Can't find genre with this name");
        }
        genreService.delete(model);
        return new BoolResponse(true, String.format("Successfully deleted genre %s", model.getName()));
    }

    @PostMapping("/admin/create")
    public BoolResponse create(CategoryCreateForm form) {
        String name = form.name();
        genreService.createGenre(name);
        return new BoolResponse(true, String.format("Successfully create genre %s", name));
    }

    @PostMapping("/update/{slug}")
    public BoolResponse update(@PathVariable String slug, CategoryCreateForm form) {
        GenreModel model = genreService.findBySlug(slug);
        if (model == null) {
            return new BoolResponse(false, "Can't find genre with this name");
        } else {
            genreService.delete(model);
        }
        return create(form);
    }

    @GetMapping("/get/{slug}")
    public ObjectResponse get(@PathVariable String slug) {
        GenreModel model = genreService.findBySlug(slug);
        if (model == null) {
            return new ObjectResponse(false, "Can't find genre with this name", null);
        }
        return new ObjectResponse(true, String.format("Successfully get genre %s", model.getName()), List.of(new SimpleRecord(model.getName(), model.getSlug())));
    }
}
