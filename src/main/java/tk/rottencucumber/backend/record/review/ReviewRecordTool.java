package tk.rottencucumber.backend.record.review;

import tk.rottencucumber.backend.model.ReviewModel;
import tk.rottencucumber.backend.record.movie.MovieRecordTool;
import tk.rottencucumber.backend.record.user.UserRecordTool;

import java.util.ArrayList;
import java.util.List;

public class ReviewRecordTool {

    public static ReviewRecord createRecord(ReviewModel model) {
        return new ReviewRecord(model.getId(), UserRecordTool.createRecord(model.getUser()), MovieRecordTool.createSimpleRecord(model.getMovie()), model.getRated(), model.getComment());
    }

    public static List<ReviewRecord> createRecordList(Iterable<ReviewModel> all) {
        List<ReviewRecord> list = new ArrayList<>();
        for (ReviewModel model : all) {
            list.add(createRecord(model));
        }
        return list;
    }
}
