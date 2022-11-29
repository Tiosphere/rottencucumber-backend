package tk.rottencucumber.backend.record.user;

import tk.rottencucumber.backend.model.UserModel;

public class UserRecordBuilder {

    public static UserRecord create(UserModel model) {
        return new UserRecord(model.getUsername(), model.getSlug(), model.getEmail(), model.is_staff());
    }
}
