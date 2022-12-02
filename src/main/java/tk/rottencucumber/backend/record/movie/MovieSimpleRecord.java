package tk.rottencucumber.backend.record.movie;

import tk.rottencucumber.backend.record.simple.SimpleRecord;

import java.util.List;

public record MovieSimpleRecord(
        Long id,
        String name,
        String slug,
        String preview,
        String summary,
        Integer views,
        Integer day,
        Integer month,
        Integer year,
        SimpleRecord language,
        List<SimpleRecord> genres,
        List<SimpleRecord> platform) {
}
