package tk.rottencucumber.backend.record.movie;

import tk.rottencucumber.backend.record.person.PersonRecord;
import tk.rottencucumber.backend.record.review.ReviewRecord;
import tk.rottencucumber.backend.record.simple.SimpleRecord;

import java.util.List;

public record MovieRecordWithReview(
        Long id,
        String name,
        String slug,
        String preview,
        String summary,
        String image,
        String type,
        Integer views,
        Integer day,
        Integer month,
        Integer year,
        SimpleRecord language,
        List<SimpleRecord> genres,
        List<SimpleRecord> platform,
        List<PersonRecord> actors,
        List<PersonRecord> directors,
        List<PersonRecord> writers,
        List<ReviewRecord> reviews,
        List<String> users
) {
}
