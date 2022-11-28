package tk.rottencucumber.backend.controller.admin;

import org.springframework.web.bind.annotation.*;
import tk.rottencucumber.backend.model.LanguageModel;
import tk.rottencucumber.backend.record.SimpleRecord;
import tk.rottencucumber.backend.record.category.CategoryCreateForm;
import tk.rottencucumber.backend.record.response.BoolResponse;
import tk.rottencucumber.backend.record.response.ObjectResponse;
import tk.rottencucumber.backend.service.LanguageService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/admin/language")
public class LanguageController {

    private final LanguageService languageService;

    public LanguageController(LanguageService languageService) {
        this.languageService = languageService;
    }

    @GetMapping("/get/all")
    public ObjectResponse getAll() {
        Iterable<LanguageModel> entities = languageService.getAll();
        List<Record> list = new ArrayList<>();
        for (LanguageModel model : entities) {
            list.add(new SimpleRecord(model.getName(), model.getSlug()));
        }
        return new ObjectResponse(true, "Successfully retrieve all languages ", list);
    }

    @PostMapping("/delete/{slug}")
    public BoolResponse delete(@PathVariable String slug) {
        LanguageModel model = languageService.findBySlug(slug);
        if (model == null) {
            return new BoolResponse(false, "Can't find language with this name");
        }
        languageService.delete(model);
        return new BoolResponse(true, String.format("Successfully deleted language %s", model.getName()));
    }

    @PostMapping("/create")
    public BoolResponse create(CategoryCreateForm form) {
        String name = form.name();
        languageService.createGenre(name);
        return new BoolResponse(true, String.format("Successfully create language %s", name));
    }

    @PostMapping("/update/{slug}")
    public BoolResponse update(@PathVariable String slug, CategoryCreateForm form) {
        LanguageModel model = languageService.findBySlug(slug);
        if (model == null) {
            return new BoolResponse(false, "Can't find language with this name");
        } else {
            languageService.delete(model);
        }
        return create(form);
    }

    @GetMapping("/get/{slug}")
    public ObjectResponse get(@PathVariable String slug) {
        LanguageModel model = languageService.findBySlug(slug);
        if (model == null) {
            return new ObjectResponse(false, "Can't find language with this name", null);
        }
        return new ObjectResponse(true, String.format("Successfully get language %s", model.getName()), List.of(new SimpleRecord(model.getName(), model.getSlug())));
    }
}
