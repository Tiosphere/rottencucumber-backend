package tk.rottencucumber.backend.record.simple;

import tk.rottencucumber.backend.record.movie.MovieRecord;

import java.util.List;

public record SimpleRecordWithMovies(Long id, String name, String slug, List<MovieRecord> movies) {
}
