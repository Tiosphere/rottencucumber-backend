package tk.rottencucumber.backend.record.person;

import org.springframework.web.multipart.MultipartFile;

public record PersonCreateForm(String name, MultipartFile image) {
}
