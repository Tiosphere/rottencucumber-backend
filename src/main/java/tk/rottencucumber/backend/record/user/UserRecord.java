package tk.rottencucumber.backend.record.user;

import com.fasterxml.jackson.annotation.JsonProperty;

public record UserRecord(String username, String slug, String email, @JsonProperty("is_staff") Boolean is_staff) {
}
