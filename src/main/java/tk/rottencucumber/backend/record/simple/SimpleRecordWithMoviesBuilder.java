package tk.rottencucumber.backend.record.simple;

import tk.rottencucumber.backend.model.GenreModel;
import tk.rottencucumber.backend.model.LanguageModel;
import tk.rottencucumber.backend.model.PlatformModel;
import tk.rottencucumber.backend.record.movie.MovieRecordTool;

public class SimpleRecordWithMoviesBuilder {

    public static SimpleRecordWithMovies create(GenreModel model) {
        return new SimpleRecordWithMovies(model.getId(), model.getName(), model.getSlug(), MovieRecordTool.getMovieRecordList(model.getMovies()));
    }

    public static SimpleRecordWithMovies create(LanguageModel model) {
        return new SimpleRecordWithMovies(model.getId(), model.getName(), model.getSlug(), MovieRecordTool.getMovieRecordList(model.getMovies()));
    }

    public static SimpleRecordWithMovies create(PlatformModel model) {
        return new SimpleRecordWithMovies(model.getId(), model.getName(), model.getSlug(), MovieRecordTool.getMovieRecordList(model.getMovies()));
    }
}
