package tk.rottencucumber.backend.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import tk.rottencucumber.backend.model.ActorModel;
import tk.rottencucumber.backend.record.BoolResponse;
import tk.rottencucumber.backend.service.ActorService;

import java.io.IOException;

@RequestMapping("/actor")
public class ActorController {

    private final ActorService actorService;

    public ActorController(ActorService actorService) {
        this.actorService = actorService;
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

    @PostMapping("/{slug}/delete")
    public BoolResponse delete(@PathVariable String slug) {
        ActorModel model = actorService.findBySlug(slug);
        if (model == null) {
            return new BoolResponse(false, "Can't find actor with this name");
        }
        actorService.delete(model);
        return new BoolResponse(true, String.format("Successfully deleted actor %s", model.getName()));
    }

    private record CreateForm(String name, MultipartFile image) {
    }
}
