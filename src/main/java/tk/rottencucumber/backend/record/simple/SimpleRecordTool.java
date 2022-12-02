package tk.rottencucumber.backend.record.simple;

import tk.rottencucumber.backend.model.GenreModel;
import tk.rottencucumber.backend.model.LanguageModel;
import tk.rottencucumber.backend.model.PlatformModel;
import tk.rottencucumber.backend.record.movie.MovieRecordTool;

import java.util.ArrayList;
import java.util.List;

public class SimpleRecordTool {

    public static SimpleRecord createRecord(GenreModel model) {
        return new SimpleRecord(model.getId(), model.getName(), model.getSlug());
    }

    public static SimpleRecord createRecord(LanguageModel model) {
        return new SimpleRecord(model.getId(), model.getName(), model.getSlug());
    }

    public static SimpleRecord createRecord(PlatformModel model) {
        return new SimpleRecord(model.getId(), model.getName(), model.getSlug());
    }

    public static SimpleRecordWithMovies createRecWithMovies(GenreModel model) {
        return new SimpleRecordWithMovies(model.getId(), model.getName(), model.getSlug(), MovieRecordTool.createRecordList(model.getMovies()));
    }

    public static SimpleRecordWithMovies createRecWithMovies(LanguageModel model) {
        return new SimpleRecordWithMovies(model.getId(), model.getName(), model.getSlug(), MovieRecordTool.createRecordList(model.getMovies()));
    }

    public static SimpleRecordWithMovies createRecWithMovies(PlatformModel model) {
        return new SimpleRecordWithMovies(model.getId(), model.getName(), model.getSlug(), MovieRecordTool.createRecordList(model.getMovies()));
    }

    public static List<SimpleRecord> createByGenreRecordList(Iterable<GenreModel> all) {
        List<SimpleRecord> list = new ArrayList<>();
        for (GenreModel model : all) {
            list.add(createRecord(model));
        }
        return list;
    }

    public static List<SimpleRecord> createByLangRecordList(Iterable<LanguageModel> all) {
        List<SimpleRecord> list = new ArrayList<>();
        for (LanguageModel model : all) {
            list.add(createRecord(model));
        }
        return list;
    }

    public static List<SimpleRecord> createByPlatRecordList(Iterable<PlatformModel> all) {
        List<SimpleRecord> list = new ArrayList<>();
        for (PlatformModel model : all) {
            list.add(createRecord(model));
        }
        return list;
    }
}
