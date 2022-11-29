package tk.rottencucumber.backend.record.user;

import com.fasterxml.jackson.annotation.JsonProperty;

public record UserCreateForm(String username, String email, String password,
                             @JsonProperty("is_staff") Boolean is_staff) {
}
