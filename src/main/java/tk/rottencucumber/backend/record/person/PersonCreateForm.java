package tk.rottencucumber.backend.record.person;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

public record PersonCreateForm(String name, MultipartFile image, @JsonProperty("birth_place") String birthPlace,
                               String description, LocalDate birthday) {
}
