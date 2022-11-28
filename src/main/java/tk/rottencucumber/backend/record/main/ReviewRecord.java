package tk.rottencucumber.backend.record.main;

import java.util.Date;

public record ReviewRecord(UserRecord user, Integer rated, Date created, String comment) {
}
