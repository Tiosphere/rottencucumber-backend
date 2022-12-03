package tk.rottencucumber.backend.record.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import tk.rottencucumber.backend.record.movie.MovieRecord;

import java.util.List;

public record UserRecordWithFav(Long id, String username, String slug, String email,
                                @JsonProperty("is_staff") Boolean is_staff, List<MovieRecord> movies) {
}
