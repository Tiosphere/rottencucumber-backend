package tk.rottencucumber.backend.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tk.rottencucumber.backend.model.WriterModel;
import tk.rottencucumber.backend.record.PersonRecord;
import tk.rottencucumber.backend.record.PersonRecordSimple;
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
        if (entities == null) {
            return new ObjectResponse(false, "Can't find writer with this name", null);
        }
        List<Record> list = new ArrayList<>();
        for (WriterModel model : entities) {
            list.add(new PersonRecord(model.getName(), model.getSlug(), Base64Encoder.encode(model.getImage()), model.getType()));
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
    public BoolResponse create(CreateForm form) {
        String name = form.name();
        MultipartFile image = form.image();
        try {
            writerService.createActor(name, image);
            return new BoolResponse(true, String.format("Successfully create writer %s", name));
        } catch (IOException e) {
            return new BoolResponse(false, "Can't process the image. Please try again");
        }
    }

    @PostMapping("/update/{slug}")
    public BoolResponse update(@PathVariable String slug, CreateForm form) {
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
        if (entities == null) {
            return new ObjectResponse(false, "Can't find writer with this name", null);
        }
        List<Record> list = new ArrayList<>();
        for (WriterModel model : entities) {
            list.add(new PersonRecordSimple(model.getName(), model.getSlug()));
        }
        return new ObjectResponse(true, "Successfully retrieve all writers ", list);
    }

    @GetMapping("/find/{name}")
    public ObjectResponse findByName(@PathVariable String name) {
        Iterable<WriterModel> entities = writerService.findByName(name);
        if (entities == null) {
            return new ObjectResponse(false, "Can't find writer with this name", null);
        }
        List<Record> list = new ArrayList<>();
        for (WriterModel model : entities) {
            list.add(new PersonRecord(model.getName(), model.getSlug(), Base64Encoder.encode(model.getImage()), model.getType()));
        }
        return new ObjectResponse(true, "Successfully retrieve all writers ", list);
    }

    @GetMapping("/find/{name}/{size}")
    public ObjectResponse findByNameLimit(@PathVariable String name, @PathVariable Integer size) {
        Iterable<WriterModel> entities = writerService.findByName(name);
        if (entities == null) {
            return new ObjectResponse(false, "Can't find writer with this name", null);
        }
        List<Record> list = new ArrayList<>();
        for (WriterModel model : entities) {
            list.add(new PersonRecord(model.getName(), model.getSlug(), Base64Encoder.encode(model.getImage()), model.getType()));
            if (list.size() == size) {
                break;
            }
        }
        return new ObjectResponse(true, "Successfully retrieve all writers ", list);
    }

    private record CreateForm(String name, MultipartFile image) {
    }
}
