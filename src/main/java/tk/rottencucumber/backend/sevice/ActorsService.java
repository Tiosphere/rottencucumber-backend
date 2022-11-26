package tk.rottencucumber.backend.sevice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.rottencucumber.backend.model.ActorModel;
import tk.rottencucumber.backend.repository.ActorsRepository;

import java.io.File;
import java.io.FileInputStream;

@Service
public class ActorsService {

    @Autowired
    private ActorsRepository actorsRepository;

    public void createActor(String name, String slug, File image) {
        ActorModel model = new ActorModel();
        model.setName(name);
        model.setSlug(slug);
        byte[] bytes = new byte[(int) image.length()];
        try {
            FileInputStream fileInputStream = new FileInputStream(image);
            fileInputStream.read(bytes);
            fileInputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        model.setImage(bytes);
        actorsRepository.save(model);
    }

    public ActorModel findByName(String name) {
        return actorsRepository.findByName(name);
    }
}

