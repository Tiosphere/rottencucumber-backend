package tk.rottencucumber.backend.record.review;

import tk.rottencucumber.backend.record.user.UserRecord;

public record ReviewRecord(Long id, UserRecord user, String comment) {
}
