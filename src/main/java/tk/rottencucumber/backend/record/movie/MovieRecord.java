package tk.rottencucumber.backend.record.movie;

import tk.rottencucumber.backend.record.person.PersonRecord;
import tk.rottencucumber.backend.record.simple.SimpleRecord;

import java.time.LocalDate;
import java.util.List;

public record MovieRecord(
        String name,
        String slug,
        String preview,
        String summary,
        String image,
        String type,
        Integer views,
        LocalDate release,
        SimpleRecord language,
        List<SimpleRecord> genres,
        List<SimpleRecord> platform,
        List<PersonRecord> actors,
        List<PersonRecord> directors,
        List<PersonRecord> writers) {
}
