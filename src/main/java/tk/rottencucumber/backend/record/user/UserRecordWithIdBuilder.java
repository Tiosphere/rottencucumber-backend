package tk.rottencucumber.backend.record.user;

import tk.rottencucumber.backend.model.UserModel;

public class UserRecordWithIdBuilder {

    public static UserRecordWithID create(UserModel model) {
        return new UserRecordWithID(model.getId(), model.getUsername(), model.getSlug(), model.getEmail(), model.is_staff());
    }
}
