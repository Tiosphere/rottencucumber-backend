package tk.rottencucumber.backend.controller.admin;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import tk.rottencucumber.backend.model.ReviewModel;
import tk.rottencucumber.backend.record.response.BoolResponse;
import tk.rottencucumber.backend.record.response.ObjectResponse;
import tk.rottencucumber.backend.record.review.ReviewRecord;
import tk.rottencucumber.backend.record.review.ReviewRecordBuilder;
import tk.rottencucumber.backend.record.review.ReviewRecordTool;
import tk.rottencucumber.backend.service.ReviewService;

import java.util.List;

@RestController
@RequestMapping("/admin/review")
@PreAuthorize(value = "hasRole('ADMIN')")
public class ReviewController {
    private final ReviewService service;

    public ReviewController(ReviewService service) {
        this.service = service;
    }

    @GetMapping("/get/all")
    public List<ReviewRecord> getAll() {
        Iterable<ReviewModel> all = service.getAll();
        return ReviewRecordTool.createReviewRecordList(all);
    }

    @PostMapping("/delete/{id}")
    public BoolResponse delete(@PathVariable Long id) {
        ReviewModel model = service.findById(id);
        if (model == null) {
            return new BoolResponse(false, "Can't find review with this id");
        }
        service.delete(model);
        return new BoolResponse(true, String.format("Successfully deleted review %d", model.getId()));
    }

    @GetMapping("/get/{id}")
    public ObjectResponse get(@PathVariable Long id) {
        ReviewModel model = service.findById(id);
        if (model == null) {
            return new ObjectResponse(false, "Can't find review with this id", null);
        }
        return new ObjectResponse(true, String.format("Successfully get review %d", model.getId()), List.of(ReviewRecordBuilder.create(model)));
    }
}
