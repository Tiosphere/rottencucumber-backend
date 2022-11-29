package tk.rottencucumber.backend.controller.admin;

import org.springframework.web.bind.annotation.*;
import tk.rottencucumber.backend.model.LanguageModel;
import tk.rottencucumber.backend.record.response.BoolResponse;
import tk.rottencucumber.backend.record.response.ObjectResponse;
import tk.rottencucumber.backend.record.simple.SimpleCreateForm;
import tk.rottencucumber.backend.record.simple.SimpleRecordWithID;
import tk.rottencucumber.backend.record.simple.SimpleRecordWithIdBuilder;
import tk.rottencucumber.backend.service.LanguageService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/admin/language")
public class LanguageController {

    private final LanguageService service;

    public LanguageController(LanguageService service) {
        this.service = service;
    }

    @GetMapping("/get/all")
    public List<SimpleRecordWithID> getAll() {
        Iterable<LanguageModel> entities = service.getAll();
        List<SimpleRecordWithID> list = new ArrayList<>();
        for (LanguageModel model : entities) {
            list.add(SimpleRecordWithIdBuilder.create(model));
        }
        return list;
    }

    @PostMapping("/delete/{slug}")
    public BoolResponse delete(@PathVariable String slug) {
        LanguageModel model = service.findBySlug(slug);
        if (model == null) {
            return new BoolResponse(false, "Can't find language with this name");
        }
        service.delete(model);
        return new BoolResponse(true, String.format("Successfully deleted language %s", model.getName()));
    }

    @PostMapping("/create")
    public BoolResponse create(SimpleCreateForm form) {
        String name = form.name();
        service.createLanguage(name);
        return new BoolResponse(true, String.format("Successfully create language %s", name));
    }

    @PostMapping("/update/{slug}")
    public BoolResponse update(@PathVariable String slug, SimpleCreateForm form) {
        LanguageModel model = service.findBySlug(slug);
        if (model == null) {
            return new BoolResponse(false, "Can't find language with this name");
        } else {
            service.update(model, form.name());
        }
        return new BoolResponse(true, String.format("Successfully create language %s", form.name()));
    }

    @GetMapping("/get/{slug}")
    public ObjectResponse get(@PathVariable String slug) {
        LanguageModel model = service.findBySlug(slug);
        if (model == null) {
            return new ObjectResponse(false, "Can't find language with this name", null);
        }
        return new ObjectResponse(true, String.format("Successfully get language %s", model.getName()), List.of(SimpleRecordWithIdBuilder.create(model)));
    }
}
