package tk.rottencucumber.backend.record.person;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;

public record PersonRecord(Long id, String name, String slug, String image, String type,
                           @JsonProperty("birth_place") String birthPlace, String description, LocalDate birthday) {
}
