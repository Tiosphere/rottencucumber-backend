package tk.rottencucumber.backend.record.person;

import tk.rottencucumber.backend.model.ActorModel;
import tk.rottencucumber.backend.model.DirectorModel;
import tk.rottencucumber.backend.model.WriterModel;
import tk.rottencucumber.backend.record.movie.MovieRecordTool;
import tk.rottencucumber.backend.util.Base64Encoder;

import java.util.ArrayList;
import java.util.List;

public class PersonRecordTool {
    public static PersonRecord createRecord(ActorModel model) {
        return new PersonRecord(model.getId(), model.getName(), model.getSlug(), Base64Encoder.encode(model.getImage()), model.getType(), model.getBirthPlace(), model.getDescription(), model.getBirthday());
    }

    public static PersonRecord createRecord(WriterModel model) {
        return new PersonRecord(model.getId(), model.getName(), model.getSlug(), Base64Encoder.encode(model.getImage()), model.getType(), model.getBirthPlace(), model.getDescription(), model.getBirthday());
    }

    public static PersonRecord createRecord(DirectorModel model) {
        return new PersonRecord(model.getId(), model.getName(), model.getSlug(), Base64Encoder.encode(model.getImage()), model.getType(), model.getBirthPlace(), model.getDescription(), model.getBirthday());
    }

    public static PersonRecordWithMovies createRecWithMovies(ActorModel model) {
        return new PersonRecordWithMovies(
                model.getId(),
                model.getName(),
                model.getSlug(),
                Base64Encoder.encode(model.getImage()),
                model.getType(),
                model.getBirthPlace(),
                model.getDescription(),
                model.getBirthday(),
                MovieRecordTool.createRecordList(model.getMovies())
        );
    }

    public static PersonRecordWithMovies createRecWithMovies(WriterModel model) {
        return new PersonRecordWithMovies(
                model.getId(),
                model.getName(),
                model.getSlug(),
                Base64Encoder.encode(model.getImage()),
                model.getType(),
                model.getBirthPlace(),
                model.getDescription(),
                model.getBirthday(),
                MovieRecordTool.createRecordList(model.getMovies())
        );
    }

    public static PersonRecordWithMovies createRecWithMovies(DirectorModel model) {
        return new PersonRecordWithMovies(
                model.getId(),
                model.getName(),
                model.getSlug(),
                Base64Encoder.encode(model.getImage()),
                model.getType(),
                model.getBirthPlace(),
                model.getDescription(),
                model.getBirthday(),
                MovieRecordTool.createRecordList(model.getMovies())
        );
    }

    public static List<PersonRecord> createByActorRecordList(Iterable<ActorModel> all) {
        List<PersonRecord> list = new ArrayList<>();
        for (ActorModel model : all) {
            list.add(createRecord(model));
        }
        return list;
    }

    public static List<PersonRecord> createByDirectorRecordList(Iterable<DirectorModel> all) {
        List<PersonRecord> list = new ArrayList<>();
        for (DirectorModel model : all) {
            list.add(createRecord(model));
        }
        return list;
    }

    public static List<PersonRecord> createByWriterRecordList(Iterable<WriterModel> all) {
        List<PersonRecord> list = new ArrayList<>();
        for (WriterModel model : all) {
            list.add(createRecord(model));
        }
        return list;
    }
}
