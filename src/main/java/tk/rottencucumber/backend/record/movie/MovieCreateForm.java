package tk.rottencucumber.backend.record.movie;

import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

public record MovieCreateForm(String name, String preview, LocalDate release, Long language, List<Long> actors,
                              List<Long> writers, List<Long> directors, List<Long> genres, List<Long> platform,
                              MultipartFile image) {
}
