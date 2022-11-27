package tk.rottencucumber.backend.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tk.rottencucumber.backend.model.WriterModel;
import tk.rottencucumber.backend.record.SimpleRecord;
import tk.rottencucumber.backend.record.person.PersonCreateForm;
import tk.rottencucumber.backend.record.person.PersonRecord;
import tk.rottencucumber.backend.record.response.BoolResponse;
import tk.rottencucumber.backend.record.response.ObjectResponse;
import tk.rottencucumber.backend.service.WriterService;
import tk.rottencucumber.backend.util.Base64Encoder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/writer")
public class WriterController {

    private final WriterService writerService;

    public WriterController(WriterService writerService) {
        this.writerService = writerService;
    }

    @GetMapping("/get/all")
    public ObjectResponse getAll() {
        Iterable<WriterModel> entities = writerService.getAll();
        List<Record> list = new ArrayList<>();
        return getObjectResponse(entities, list);
    }

    private ObjectResponse getObjectResponse(Iterable<WriterModel> entities, List<Record> list) {
        for (WriterModel model : entities) {
            list.add(new PersonRecord(model.getName(), model.getSlug(), Base64Encoder.encode(model.getImage()), model.getType()));
        }
        if (list.isEmpty()) {
            return new ObjectResponse(false, "Can't find writer with this name", null);
        }
        return new ObjectResponse(true, "Successfully retrieve all writers ", list);
    }

    @PostMapping("/delete/{slug}")
    public BoolResponse delete(@PathVariable String slug) {
        WriterModel model = writerService.findBySlug(slug);
        if (model == null) {
            return new BoolResponse(false, "Can't find writer with this name");
        }
        writerService.delete(model);
        return new BoolResponse(true, String.format("Successfully deleted writer %s", model.getName()));
    }

    @PostMapping("/create")
    public BoolResponse create(PersonCreateForm form) {
        String name = form.name();
        MultipartFile image = form.image();
        try {
            writerService.createWriter(name, image);
            return new BoolResponse(true, String.format("Successfully create writer %s", name));
        } catch (IOException e) {
            return new BoolResponse(false, "Can't process the image. Please try again");
        }
    }

    @PostMapping("/update/{slug}")
    public BoolResponse update(@PathVariable String slug, PersonCreateForm form) {
        WriterModel model = writerService.findBySlug(slug);
        if (model == null) {
            return new BoolResponse(false, "Can't find writer with this name");
        } else {
            writerService.delete(model);
        }
        return create(form);
    }

    @GetMapping("/get/{slug}")
    public ObjectResponse get(@PathVariable String slug) {
        WriterModel model = writerService.findBySlug(slug);
        if (model == null) {
            return new ObjectResponse(false, "Can't find writer with this name", null);
        }
        PersonRecord record = new PersonRecord(model.getName(), model.getSlug(), Base64Encoder.encode(model.getImage()), model.getType());
        return new ObjectResponse(true, String.format("Successfully get writer %s", model.getName()), List.of(record));
    }

    /*
     * same as normal get all but not include image data
     */
    @GetMapping("/get/all/simple")
    public ObjectResponse getAllSimple() {
        Iterable<WriterModel> entities = writerService.getAll();
        List<Record> list = new ArrayList<>();
        for (WriterModel model : entities) {
            list.add(new SimpleRecord(model.getName(), model.getSlug()));
        }
        if (list.isEmpty()) {
            return new ObjectResponse(false, "Can't find writer with this name", null);
        }
        return new ObjectResponse(true, "Successfully retrieve all writers ", list);
    }

    @GetMapping("/find/{name}")
    public ObjectResponse findByName(@PathVariable String name) {
        Iterable<WriterModel> entities = writerService.findByName(name);
        List<Record> list = new ArrayList<>();
        return getObjectResponse(entities, list);
    }

    @GetMapping("/find/{name}/{size}")
    public ObjectResponse findByNameLimit(@PathVariable String name, @PathVariable Integer size) {
        Iterable<WriterModel> entities = writerService.findByName(name);
        List<Record> list = new ArrayList<>();
        for (WriterModel model : entities) {
            list.add(new PersonRecord(model.getName(), model.getSlug(), Base64Encoder.encode(model.getImage()), model.getType()));
            if (list.size() == size) {
                break;
            }
        }
        if (list.isEmpty()) {
            return new ObjectResponse(false, "Can't find writer with this name", null);
        }
        return new ObjectResponse(true, "Successfully retrieve all writers ", list);
    }
}
