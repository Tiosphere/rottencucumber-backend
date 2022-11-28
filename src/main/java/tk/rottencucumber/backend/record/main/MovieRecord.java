package tk.rottencucumber.backend.record.main;

public record MovieRecord(String name, String slug, String image, String type, String preview, String summary,
                          java.time.LocalDate release) {
}
