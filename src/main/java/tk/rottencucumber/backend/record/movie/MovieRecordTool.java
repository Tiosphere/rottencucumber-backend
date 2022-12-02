package tk.rottencucumber.backend.record.movie;

import tk.rottencucumber.backend.model.MovieModel;
import tk.rottencucumber.backend.record.person.PersonRecordTool;
import tk.rottencucumber.backend.record.review.ReviewRecordTool;
import tk.rottencucumber.backend.record.simple.SimpleRecordTool;
import tk.rottencucumber.backend.util.Base64Encoder;

import java.util.ArrayList;
import java.util.List;

public class MovieRecordTool {

    public static MovieSimpleRecord createSimpleRecord(MovieModel model) {
        return new MovieSimpleRecord(
                model.getId(),
                model.getName(),
                model.getSlug(),
                model.getPreview(),
                model.getSummary(),
                model.getViews(),
                model.getRelease().getDayOfMonth(),
                model.getRelease().getMonth().getValue(),
                model.getRelease().getYear(),
                SimpleRecordTool.createRecord(model.getLanguage()),
                SimpleRecordTool.createByGenreRecordList(model.getGenres()),
                SimpleRecordTool.createByPlatRecordList(model.getPlatforms())
        );
    }

    public static MovieRecord createRecord(MovieModel model) {
        return new MovieRecord(
                model.getId(),
                model.getName(),
                model.getSlug(),
                model.getPreview(),
                model.getSummary(),
                Base64Encoder.encode(model.getImage()),
                model.getType(),
                model.getViews(),
                model.getRelease().getDayOfMonth(),
                model.getRelease().getMonth().getValue(),
                model.getRelease().getYear(),
                SimpleRecordTool.createRecord(model.getLanguage()),
                SimpleRecordTool.createByGenreRecordList(model.getGenres()),
                SimpleRecordTool.createByPlatRecordList(model.getPlatforms()),
                PersonRecordTool.createByActorRecordList(model.getActors()),
                PersonRecordTool.createByDirectorRecordList(model.getDirectors()),
                PersonRecordTool.createByWriterRecordList(model.getWriters())
        );
    }

    public static MovieRecordWithReview createRecWithReview(MovieModel model) {
        return new MovieRecordWithReview(
                model.getId(),
                model.getName(),
                model.getSlug(),
                model.getPreview(),
                model.getSummary(),
                Base64Encoder.encode(model.getImage()),
                model.getType(),
                model.getViews(),
                model.getRelease().getDayOfMonth(),
                model.getRelease().getMonth().getValue(),
                model.getRelease().getYear(),
                SimpleRecordTool.createRecord(model.getLanguage()),
                SimpleRecordTool.createByGenreRecordList(model.getGenres()),
                SimpleRecordTool.createByPlatRecordList(model.getPlatforms()),
                PersonRecordTool.createByActorRecordList(model.getActors()),
                PersonRecordTool.createByDirectorRecordList(model.getDirectors()),
                PersonRecordTool.createByWriterRecordList(model.getWriters()),
                ReviewRecordTool.createRecordList(model.getReviews())
        );
    }

    public static List<MovieRecord> createRecordList(Iterable<MovieModel> all) {
        List<MovieRecord> list = new ArrayList<>();
        for (MovieModel model : all) {
            list.add(createRecord(model));
        }
        return list;
    }

    public static List<MovieSimpleRecord> createSimpleRecordList(Iterable<MovieModel> all) {
        List<MovieSimpleRecord> list = new ArrayList<>();
        for (MovieModel model : all) {
            list.add(createSimpleRecord(model));
        }
        return list;
    }
}
