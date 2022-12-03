package tk.rottencucumber.backend.record.user;

import tk.rottencucumber.backend.model.UserModel;
import tk.rottencucumber.backend.record.movie.MovieRecordTool;

import java.util.ArrayList;
import java.util.List;

public class UserRecordTool {

    public static UserRecord createRecord(UserModel model) {
        return new UserRecord(model.getId(), model.getUsername(), model.getSlug(), model.getEmail(), model.is_staff());
    }

    public static UserRecordWithFav createRecWithFav(UserModel model) {
        return new UserRecordWithFav(model.getId(), model.getUsername(), model.getSlug(), model.getEmail(), model.is_staff(), MovieRecordTool.createRecordList(model.getMovies()));
    }

    public static List<UserRecord> createUserRecordList(Iterable<UserModel> all) {
        List<UserRecord> list = new ArrayList<>();
        for (UserModel model : all) {
            list.add(createRecord(model));
        }
        return list;
    }
}
