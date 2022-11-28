package tk.rottencucumber.backend.record.main;

import com.fasterxml.jackson.annotation.JsonProperty;

public record UserRecord(String username, String slug, String image, String type,
                         @JsonProperty("is_staff") Boolean is_staff) {
}
