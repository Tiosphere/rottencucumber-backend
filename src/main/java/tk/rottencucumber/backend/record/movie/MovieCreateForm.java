package tk.rottencucumber.backend.record.movie;

import org.springframework.web.multipart.MultipartFile;

import java.util.Set;

public record MovieCreateForm(String name, String preview,
                              Integer day, Integer month, Integer year,
                              Long language,
                              Set<Long> actors,
                              Set<Long> writers,
                              Set<Long> directors,
                              Set<Long> genres,
                              Set<Long> platform,
                              MultipartFile image, String summary) {
}
