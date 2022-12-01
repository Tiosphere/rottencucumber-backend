package tk.rottencucumber.backend.controller.admin;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import tk.rottencucumber.backend.model.GenreModel;
import tk.rottencucumber.backend.record.response.BoolResponse;
import tk.rottencucumber.backend.record.response.ObjectResponse;
import tk.rottencucumber.backend.record.simple.SimpleCreateForm;
import tk.rottencucumber.backend.record.simple.SimpleRecord;
import tk.rottencucumber.backend.record.simple.SimpleRecordBuilder;
import tk.rottencucumber.backend.record.simple.SimpleRecordTool;
import tk.rottencucumber.backend.service.GenreService;

import java.util.List;

@RestController
@RequestMapping("/admin/genre")
@PreAuthorize(value = "hasRole('ADMIN')")
public class GenreController {

    private final GenreService service;

    public GenreController(GenreService service) {
        this.service = service;
    }

    @GetMapping("/get/all")
    public List<SimpleRecord> getAll() {
        Iterable<GenreModel> all = service.getAll();
        return SimpleRecordTool.createRecordsWithGen(all);
    }

    @PostMapping("/delete/{slug}")
    public BoolResponse delete(@PathVariable String slug) {
        GenreModel model = service.findBySlug(slug);
        if (model == null) {
            return new BoolResponse(false, "Can't find genre with this name");
        }
        service.delete(model);
        return new BoolResponse(true, String.format("Successfully deleted genre %s", model.getName()));
    }

    @PostMapping("/create")
    public BoolResponse create(SimpleCreateForm form) {
        String name = form.name();
        service.createGenre(name);
        return new BoolResponse(true, String.format("Successfully create genre %s", name));
    }

    @PostMapping("/update/{slug}")
    public BoolResponse update(@PathVariable String slug, SimpleCreateForm form) {
        GenreModel model = service.findBySlug(slug);
        if (model == null) {
            return new BoolResponse(false, "Can't find genre with this name");
        } else {
            service.update(model, form.name());
        }
        return new BoolResponse(true, String.format("Successfully create genre %s", form.name()));
    }

    @GetMapping("/get/{slug}")
    public ObjectResponse get(@PathVariable String slug) {
        GenreModel model = service.findBySlug(slug);
        if (model == null) {
            return new ObjectResponse(false, "Can't find genre with this name", null);
        }
        return new ObjectResponse(true, String.format("Successfully get genre %s", model.getName()), List.of(SimpleRecordBuilder.create(model)));
    }
}
