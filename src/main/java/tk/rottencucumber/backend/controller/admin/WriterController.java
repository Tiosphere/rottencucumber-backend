package tk.rottencucumber.backend.controller.admin;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tk.rottencucumber.backend.model.WriterModel;
import tk.rottencucumber.backend.record.person.PersonCreateForm;
import tk.rottencucumber.backend.record.person.PersonRecordWithID;
import tk.rottencucumber.backend.record.person.PersonRecordWithIdBuilder;
import tk.rottencucumber.backend.record.response.BoolResponse;
import tk.rottencucumber.backend.record.response.ObjectResponse;
import tk.rottencucumber.backend.service.WriterService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/admin/writer")
@PreAuthorize(value = "hasRole('ADMIN')")
public class WriterController {
    private final WriterService service;

    public WriterController(WriterService service) {
        this.service = service;
    }

    @GetMapping("/get/all")
    public List<PersonRecordWithID> getAll() {
        Iterable<WriterModel> entities = service.getAll();
        List<PersonRecordWithID> list = new ArrayList<>();
        for (WriterModel model : entities) {
            list.add(PersonRecordWithIdBuilder.create(model));
        }
        return list;
    }

    @PostMapping("/delete/{slug}")
    public BoolResponse delete(@PathVariable String slug) {
        WriterModel model = service.findBySlug(slug);
        if (model == null) {
            return new BoolResponse(false, "Can't find writer with this name");
        }
        service.delete(model);
        return new BoolResponse(true, String.format("Successfully deleted writer %s", model.getName()));
    }

    @PostMapping("/create")
    public BoolResponse create(PersonCreateForm form) {
        String name = form.name();
        MultipartFile image = form.image();
        try {
            service.createWriter(name, image);
            return new BoolResponse(true, String.format("Successfully create writer %s", name));
        } catch (IOException e) {
            return new BoolResponse(false, "Can't process the image. Please try again");
        }
    }

    @PostMapping("/update/{slug}")
    public BoolResponse update(@PathVariable String slug, PersonCreateForm form) {
        WriterModel model = service.findBySlug(slug);
        if (model == null) {
            return new BoolResponse(false, "Can't find writer with this name");
        } else {
            try {
                service.update(model, form.name(), form.image());
            } catch (IOException e) {
                return new BoolResponse(false, "Can't process the image. Please try again");
            }
        }
        return new BoolResponse(true, String.format("Successfully create writer %s", model.getName()));
    }

    @GetMapping("/get/{slug}")
    public ObjectResponse get(@PathVariable String slug) {
        WriterModel model = service.findBySlug(slug);
        if (model == null) {
            return new ObjectResponse(false, "Can't find writer with this name", null);
        }
        return new ObjectResponse(true, String.format("Successfully get writer %s", model.getName()), List.of(PersonRecordWithIdBuilder.create(model)));
    }
}
