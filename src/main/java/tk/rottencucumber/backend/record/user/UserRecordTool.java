package tk.rottencucumber.backend.record.user;

import tk.rottencucumber.backend.model.UserModel;

import java.util.ArrayList;
import java.util.List;

public class UserRecordTool {

    public static UserRecord createRecord(UserModel model) {
        return new UserRecord(model.getId(), model.getUsername(), model.getSlug(), model.getEmail(), model.is_staff());
    }

    public static List<UserRecord> createUserRecordList(Iterable<UserModel> all) {
        List<UserRecord> list = new ArrayList<>();
        for (UserModel model : all) {
            list.add(createRecord(model));
        }
        return list;
    }
}
