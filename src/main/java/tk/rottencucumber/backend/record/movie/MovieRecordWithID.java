package tk.rottencucumber.backend.record.movie;

import tk.rottencucumber.backend.record.person.PersonRecordWithID;
import tk.rottencucumber.backend.record.simple.SimpleRecordWithID;

import java.time.LocalDate;
import java.util.List;

public record MovieRecordWithID(
        Long id,
        String name,
        String slug,
        String preview,
        String summary,
        String image,
        String type,
        Integer views,
        LocalDate release,
        SimpleRecordWithID language,
        List<SimpleRecordWithID> genres,
        List<SimpleRecordWithID> platform,
        List<PersonRecordWithID> actors,
        List<PersonRecordWithID> directors,
        List<PersonRecordWithID> writers) {
}
