package tk.rottencucumber.backend.controller.admin;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import tk.rottencucumber.backend.model.UserModel;
import tk.rottencucumber.backend.record.response.BoolResponse;
import tk.rottencucumber.backend.record.response.ObjectResponse;
import tk.rottencucumber.backend.record.user.*;
import tk.rottencucumber.backend.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/admin/user")
@PreAuthorize(value = "hasRole('ADMIN')")
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @PostMapping("/create")
    public BoolResponse create(UserCreateForm form) {
        service.createUser(form.username(), form.email(), form.password(), form.is_staff());
        return new BoolResponse(true, String.format("Successfully create user %s", form.username()));
    }

    @GetMapping("/get/all")
    public List<UserRecord> getAll() {
        Iterable<UserModel> all = service.getAll();
        return UserRecordTool.createUserRecordList(all);
    }

    @GetMapping("/get/{slug}")
    public ObjectResponse get(@PathVariable String slug) {
        UserModel model = service.findBySlug(slug);
        if (model == null) {
            return new ObjectResponse(false, "Can't find user with this name", null);
        }
        return new ObjectResponse(true, String.format("Successfully get user %s", model.getUsername()), List.of(UserRecordBuilder.create(model)));
    }

    @PostMapping("/update/{slug}")
    public BoolResponse update(@PathVariable String slug, UserUpdateForm form) {
        UserModel model = service.findBySlug(slug);
        if (model == null) {
            return new BoolResponse(false, "Can't find user with this name");
        } else {
            service.update(model, form.username(), form.email());
        }
        return new BoolResponse(true, String.format("Successfully create user %s", form.username()));
    }

    @PostMapping("/delete/{slug}")
    public BoolResponse delete(@PathVariable String slug) {
        UserModel model = service.findBySlug(slug);
        if (model == null) {
            return new BoolResponse(false, "Can't find language with this name");
        }
        service.delete(model);
        return new BoolResponse(true, String.format("Successfully deleted language %s", model.getUsername()));
    }
}
