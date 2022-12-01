package tk.rottencucumber.backend.service;

import org.springframework.stereotype.Service;
import tk.rottencucumber.backend.model.ReviewModel;
import tk.rottencucumber.backend.record.person.PersonCreateForm;
import tk.rottencucumber.backend.repository.ReviewRepository;

@Service
public class ReviewService {
    private final ReviewRepository repository;

    public ReviewService(ReviewRepository repository) {
        this.repository = repository;
    }

    public void createReview(PersonCreateForm form) {
    }

    public ReviewModel findById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public void delete(ReviewModel entity) {
        repository.delete(entity);
    }

    public Iterable<ReviewModel> getAll() {
        return repository.findAll();
    }

}

