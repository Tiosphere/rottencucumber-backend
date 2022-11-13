package tk.rottencucumber.backend.sevice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.rottencucumber.backend.repository.ActorsRepository;

@Service
public class ActorsService {

    @Autowired
    private ActorsRepository actorsRepository;
}

