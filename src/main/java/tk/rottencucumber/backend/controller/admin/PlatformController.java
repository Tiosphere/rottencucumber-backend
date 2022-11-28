package tk.rottencucumber.backend.controller.admin;

import org.springframework.web.bind.annotation.*;
import tk.rottencucumber.backend.model.PlatformModel;
import tk.rottencucumber.backend.record.SimpleRecord;
import tk.rottencucumber.backend.record.category.CategoryCreateForm;
import tk.rottencucumber.backend.record.response.BoolResponse;
import tk.rottencucumber.backend.record.response.ObjectResponse;
import tk.rottencucumber.backend.service.PlatformService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/admin/platform")
public class PlatformController {

    private final PlatformService platformService;

    public PlatformController(PlatformService platformService) {
        this.platformService = platformService;
    }

    @GetMapping("/get/all")
    public ObjectResponse getAll() {
        Iterable<PlatformModel> entities = platformService.getAll();
        List<Record> list = new ArrayList<>();
        for (PlatformModel model : entities) {
            list.add(new SimpleRecord(model.getName(), model.getSlug()));
        }
        return new ObjectResponse(true, "Successfully retrieve all platforms ", list);
    }

    @PostMapping("/delete/{slug}")
    public BoolResponse delete(@PathVariable String slug) {
        PlatformModel model = platformService.findBySlug(slug);
        if (model == null) {
            return new BoolResponse(false, "Can't find platform with this name");
        }
        platformService.delete(model);
        return new BoolResponse(true, String.format("Successfully deleted platform %s", model.getName()));
    }

    @PostMapping("/create")
    public BoolResponse create(CategoryCreateForm form) {
        String name = form.name();
        platformService.createPlatform(name);
        return new BoolResponse(true, String.format("Successfully create platform %s", name));
    }

    @PostMapping("/update/{slug}")
    public BoolResponse update(@PathVariable String slug, CategoryCreateForm form) {
        PlatformModel model = platformService.findBySlug(slug);
        if (model == null) {
            return new BoolResponse(false, "Can't find platform with this name");
        } else {
            platformService.delete(model);
        }
        return create(form);
    }

    @GetMapping("/get/{slug}")
    public ObjectResponse get(@PathVariable String slug) {
        PlatformModel model = platformService.findBySlug(slug);
        if (model == null) {
            return new ObjectResponse(false, "Can't find platform with this name", null);
        }
        return new ObjectResponse(true, String.format("Successfully get platform %s", model.getName()), List.of(new SimpleRecord(model.getName(), model.getSlug())));
    }
}
