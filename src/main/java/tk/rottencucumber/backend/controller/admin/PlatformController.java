package tk.rottencucumber.backend.controller.admin;

import org.springframework.web.bind.annotation.*;
import tk.rottencucumber.backend.model.PlatformModel;
import tk.rottencucumber.backend.record.response.BoolResponse;
import tk.rottencucumber.backend.record.response.ObjectResponse;
import tk.rottencucumber.backend.record.simple.SimpleCreateForm;
import tk.rottencucumber.backend.record.simple.SimpleRecordWithID;
import tk.rottencucumber.backend.record.simple.SimpleRecordWithIdBuilder;
import tk.rottencucumber.backend.service.PlatformService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/genre")
public class PlatformController {

    private final PlatformService service;

    public PlatformController(PlatformService service) {
        this.service = service;
    }

    @GetMapping("/get/all")
    public List<SimpleRecordWithID> getAll() {
        Iterable<PlatformModel> entities = service.getAll();
        List<SimpleRecordWithID> list = new ArrayList<>();
        for (PlatformModel model : entities) {
            list.add(SimpleRecordWithIdBuilder.create(model));
        }
        return list;
    }

    @PostMapping("/delete/{slug}")
    public BoolResponse delete(@PathVariable String slug) {
        PlatformModel model = service.findBySlug(slug);
        if (model == null) {
            return new BoolResponse(false, "Can't find genre with this name");
        }
        service.delete(model);
        return new BoolResponse(true, String.format("Successfully deleted genre %s", model.getName()));
    }

    @PostMapping("/admin/create")
    public BoolResponse create(SimpleCreateForm form) {
        String name = form.name();
        service.createPlatform(name);
        return new BoolResponse(true, String.format("Successfully create genre %s", name));
    }

    @PostMapping("/update/{slug}")
    public BoolResponse update(@PathVariable String slug, SimpleCreateForm form) {
        PlatformModel model = service.findBySlug(slug);
        if (model == null) {
            return new BoolResponse(false, "Can't find genre with this name");
        } else {
            service.update(model, form.name());
        }
        return new BoolResponse(true, String.format("Successfully create genre %s", form.name()));
    }

    @GetMapping("/get/{slug}")
    public ObjectResponse get(@PathVariable String slug) {
        PlatformModel model = service.findBySlug(slug);
        if (model == null) {
            return new ObjectResponse(false, "Can't find genre with this name", null);
        }
        return new ObjectResponse(true, String.format("Successfully get genre %s", model.getName()), List.of(SimpleRecordWithIdBuilder.create(model)));
    }
}
