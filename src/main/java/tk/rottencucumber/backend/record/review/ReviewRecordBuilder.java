package tk.rottencucumber.backend.record.review;

import tk.rottencucumber.backend.model.ReviewModel;
import tk.rottencucumber.backend.record.user.UserRecordBuilder;

public class ReviewRecordBuilder {
    public static ReviewRecord create(ReviewModel model) {
        return new ReviewRecord(model.getId(), UserRecordBuilder.create(model.getUser()), model.getMovie().getId(), model.getRated(), model.getComment());
    }
}
