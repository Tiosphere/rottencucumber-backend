package tk.rottencucumber.backend.record.main;

import java.util.Date;

public record MovieRecord(String name, String slug, String image, String type, String preview, String summary,
                          Date release) {
}
