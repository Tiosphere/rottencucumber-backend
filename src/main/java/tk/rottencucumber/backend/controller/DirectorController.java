package tk.rottencucumber.backend.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tk.rottencucumber.backend.model.DirectorModel;
import tk.rottencucumber.backend.record.PersonRecord;
import tk.rottencucumber.backend.record.PersonRecordSimple;
import tk.rottencucumber.backend.record.response.BoolResponse;
import tk.rottencucumber.backend.record.response.ObjectResponse;
import tk.rottencucumber.backend.service.DirectorService;
import tk.rottencucumber.backend.util.Base64Encoder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/director")
public class DirectorController {

    private final DirectorService directorService;

    public DirectorController(DirectorService directorService) {
        this.directorService = directorService;
    }

    @GetMapping("/get/all")
    public ObjectResponse getAll() {
        Iterable<DirectorModel> entities = directorService.getAll();
        if (entities == null) {
            return new ObjectResponse(false, "Can't find director with this name", null);
        }
        List<Record> list = new ArrayList<>();
        for (DirectorModel model : entities) {
            list.add(new PersonRecord(model.getName(), model.getSlug(), Base64Encoder.encode(model.getImage()), model.getType()));
        }
        return new ObjectResponse(true, "Successfully retrieve all directors ", list);
    }

    @PostMapping("/delete/{slug}")
    public BoolResponse delete(@PathVariable String slug) {
        DirectorModel model = directorService.findBySlug(slug);
        if (model == null) {
            return new BoolResponse(false, "Can't find director with this name");
        }
        directorService.delete(model);
        return new BoolResponse(true, String.format("Successfully deleted director %s", model.getName()));
    }

    @PostMapping("/create")
    public BoolResponse create(CreateForm form) {
        String name = form.name();
        MultipartFile image = form.image();
        try {
            directorService.createActor(name, image);
            return new BoolResponse(true, String.format("Successfully create director %s", name));
        } catch (IOException e) {
            return new BoolResponse(false, "Can't process the image. Please try again");
        }
    }

    @PostMapping("/update/{slug}")
    public BoolResponse update(@PathVariable String slug, CreateForm form) {
        DirectorModel model = directorService.findBySlug(slug);
        if (model == null) {
            return new BoolResponse(false, "Can't find director with this name");
        } else {
            directorService.delete(model);
        }
        return create(form);
    }

    @GetMapping("/get/{slug}")
    public ObjectResponse get(@PathVariable String slug) {
        DirectorModel model = directorService.findBySlug(slug);
        if (model == null) {
            return new ObjectResponse(false, "Can't find director with this name", null);
        }
        PersonRecord record = new PersonRecord(model.getName(), model.getSlug(), Base64Encoder.encode(model.getImage()), model.getType());
        return new ObjectResponse(true, String.format("Successfully get director %s", model.getName()), List.of(record));
    }

    /*
     * same as normal get all but not include image data
     */
    @GetMapping("/get/all/simple")
    public ObjectResponse getAllSimple() {
        Iterable<DirectorModel> entities = directorService.getAll();
        List<Record> list = new ArrayList<>();
        for (DirectorModel model : entities) {
            list.add(new PersonRecordSimple(model.getName(), model.getSlug()));
        }
        if (list.isEmpty()) {
            return new ObjectResponse(false, "Can't find director with this name", null);
        }
        return new ObjectResponse(true, "Successfully retrieve all directors ", list);
    }

    @GetMapping("/find/{name}")
    public ObjectResponse findByName(@PathVariable String name) {
        Iterable<DirectorModel> entities = directorService.findByName(name);
        List<Record> list = new ArrayList<>();
        for (DirectorModel model : entities) {
            list.add(new PersonRecord(model.getName(), model.getSlug(), Base64Encoder.encode(model.getImage()), model.getType()));
        }
        if (list.isEmpty()) {
            return new ObjectResponse(false, "Can't find director with this name", null);
        }
        return new ObjectResponse(true, "Successfully retrieve all directors ", list);
    }

    @GetMapping("/find/{name}/{size}")
    public ObjectResponse findByNameLimit(@PathVariable String name, @PathVariable Integer size) {
        Iterable<DirectorModel> entities = directorService.findByName(name);
        List<Record> list = new ArrayList<>();
        for (DirectorModel model : entities) {
            list.add(new PersonRecord(model.getName(), model.getSlug(), Base64Encoder.encode(model.getImage()), model.getType()));
            if (list.size() == size) {
                break;
            }
        }
        if (list.isEmpty()) {
            return new ObjectResponse(false, "Can't find director with this name", null);
        }
        return new ObjectResponse(true, "Successfully retrieve all directors ", list);
    }

    private record CreateForm(String name, MultipartFile image) {
    }
}
