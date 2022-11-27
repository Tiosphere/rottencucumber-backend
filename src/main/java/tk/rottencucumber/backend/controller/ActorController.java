package tk.rottencucumber.backend.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tk.rottencucumber.backend.model.ActorModel;
import tk.rottencucumber.backend.record.ActorRecord;
import tk.rottencucumber.backend.record.ActorRecordSimple;
import tk.rottencucumber.backend.record.response.BoolResponse;
import tk.rottencucumber.backend.record.response.ObjectResponse;
import tk.rottencucumber.backend.service.ActorService;
import tk.rottencucumber.backend.util.Base64Encoder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/actor")
public class ActorController {

    private final ActorService actorService;

    public ActorController(ActorService actorService) {
        this.actorService = actorService;
    }

    @GetMapping("/get/all")
    public ObjectResponse getAll() {
        Iterable<ActorModel> entities = actorService.getAll();
        if (entities == null) {
            return new ObjectResponse(false, "Can't find actor with this name", null);
        }
        List<Record> list = new ArrayList<>();
        for (ActorModel model : entities) {
            list.add(new ActorRecord(model.getName(), model.getSlug(), Base64Encoder.encode(model.getImage()), model.getType()));
        }
        return new ObjectResponse(true, "Successfully retrieve all actors ", list);
    }

    @PostMapping("/delete/{slug}")
    public BoolResponse delete(@PathVariable String slug) {
        ActorModel model = actorService.findBySlug(slug);
        if (model == null) {
            return new BoolResponse(false, "Can't find actor with this name");
        }
        actorService.delete(model);
        return new BoolResponse(true, String.format("Successfully deleted actor %s", model.getName()));
    }

    @PostMapping("/create")
    public BoolResponse create(CreateForm form) {
        String name = form.name();
        MultipartFile image = form.image();
        try {
            actorService.createActor(name, image);
            return new BoolResponse(true, String.format("Successfully create actor %s", name));
        } catch (IOException e) {
            return new BoolResponse(false, "Can't process the image. Please try again");
        }
    }

    @PostMapping("/update/{slug}")
    public BoolResponse update(@PathVariable String slug, CreateForm form) {
        ActorModel model = actorService.findBySlug(slug);
        if (model == null) {
            return new BoolResponse(false, "Can't find actor with this name");
        } else {
            actorService.delete(model);
        }
        return create(form);
    }

    @GetMapping("/get/{slug}")
    public ObjectResponse get(@PathVariable String slug) {
        ActorModel model = actorService.findBySlug(slug);
        if (model == null) {
            return new ObjectResponse(false, "Can't find actor with this name", null);
        }
        ActorRecord record = new ActorRecord(model.getName(), model.getSlug(), Base64Encoder.encode(model.getImage()), model.getType());
        return new ObjectResponse(true, String.format("Successfully get actor %s", model.getName()), List.of(record));
    }

    /*
     * same as normal get all but not include image data
     */
    @GetMapping("/get/all/simple")
    public ObjectResponse getAllSimple() {
        Iterable<ActorModel> entities = actorService.getAll();
        if (entities == null) {
            return new ObjectResponse(false, "Can't find actor with this name", null);
        }
        List<Record> list = new ArrayList<>();
        for (ActorModel model : entities) {
            list.add(new ActorRecordSimple(model.getName(), model.getSlug()));
        }
        return new ObjectResponse(true, "Successfully retrieve all actors ", list);
    }

    @GetMapping("/find/{name}")
    public ObjectResponse findByName(@PathVariable String name) {
        Iterable<ActorModel> entities = actorService.findByName(name);
        if (entities == null) {
            return new ObjectResponse(false, "Can't find actor with this name", null);
        }
        List<Record> list = new ArrayList<>();
        for (ActorModel model : entities) {
            list.add(new ActorRecord(model.getName(), model.getSlug(), Base64Encoder.encode(model.getImage()), model.getType()));
        }
        return new ObjectResponse(true, "Successfully retrieve all actors ", list);
    }

    @GetMapping("/find/{name}/{size}")
    public ObjectResponse findByNameLimit(@PathVariable String name, @PathVariable Integer size) {
        Iterable<ActorModel> entities = actorService.findByName(name);
        if (entities == null) {
            return new ObjectResponse(false, "Can't find actor with this name", null);
        }
        List<Record> list = new ArrayList<>();
        for (ActorModel model : entities) {
            list.add(new ActorRecord(model.getName(), model.getSlug(), Base64Encoder.encode(model.getImage()), model.getType()));
            if (list.size() == size) {
                break;
            }
        }
        return new ObjectResponse(true, "Successfully retrieve all actors ", list);
    }

    private record CreateForm(String name, MultipartFile image) {
    }
}
