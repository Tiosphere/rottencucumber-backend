package tk.rottencucumber.backend.record.review;

import tk.rottencucumber.backend.model.ReviewModel;

import java.util.ArrayList;
import java.util.List;

public class ReviewRecordTool {
    public static List<ReviewRecord> createReviewRecordList(Iterable<ReviewModel> all) {
        List<ReviewRecord> list = new ArrayList<>();
        for (ReviewModel model : all) {
            list.add(ReviewRecordBuilder.create(model));
        }
        return list;
    }
}
