package tk.rottencucumber.backend.record.user;

import com.fasterxml.jackson.annotation.JsonProperty;

public record UserRecordWithID(Long id, String username, String slug, String email,
                               @JsonProperty("is_staff") Boolean is_staff) {
}
