package tk.rottencucumber.backend.record.person;

import com.fasterxml.jackson.annotation.JsonProperty;
import tk.rottencucumber.backend.record.movie.MovieRecord;

import java.time.LocalDate;
import java.util.List;

public record PersonRecordWithMovies(Long id, String name, String slug, String image, String type,
                                     @JsonProperty("birth_place") String birthPlace, String description,
                                     LocalDate birthday, List<MovieRecord> movies) {
}
