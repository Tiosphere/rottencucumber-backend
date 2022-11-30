package tk.rottencucumber.backend.record.person;

import tk.rottencucumber.backend.model.ActorModel;
import tk.rottencucumber.backend.model.DirectorModel;
import tk.rottencucumber.backend.model.WriterModel;
import tk.rottencucumber.backend.util.Base64Encoder;
import tk.rottencucumber.backend.util.MovieRecordTool;

public class PersonRecordWithMoviesBuilder {
    public static PersonRecordWithMovies create(ActorModel model) {
        return new PersonRecordWithMovies(
                model.getId(),
                model.getName(),
                model.getSlug(),
                Base64Encoder.encode(model.getImage()),
                model.getType(),
                model.getBirthPlace(),
                model.getDescription(),
                model.getBirthday(),
                MovieRecordTool.getMovieRecords(model.getMovies())
        );
    }

    public static PersonRecordWithMovies create(WriterModel model) {
        return new PersonRecordWithMovies(
                model.getId(),
                model.getName(),
                model.getSlug(),
                Base64Encoder.encode(model.getImage()),
                model.getType(),
                model.getBirthPlace(),
                model.getDescription(),
                model.getBirthday(),
                MovieRecordTool.getMovieRecords(model.getMovies())
        );
    }

    public static PersonRecordWithMovies create(DirectorModel model) {
        return new PersonRecordWithMovies(
                model.getId(),
                model.getName(),
                model.getSlug(),
                Base64Encoder.encode(model.getImage()),
                model.getType(),
                model.getBirthPlace(),
                model.getDescription(),
                model.getBirthday(),
                MovieRecordTool.getMovieRecords(model.getMovies())
        );
    }
}
