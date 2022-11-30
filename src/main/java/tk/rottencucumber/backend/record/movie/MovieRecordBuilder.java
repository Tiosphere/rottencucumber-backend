package tk.rottencucumber.backend.record.movie;

import tk.rottencucumber.backend.model.*;
import tk.rottencucumber.backend.record.person.PersonRecord;
import tk.rottencucumber.backend.record.person.PersonRecordBuilder;
import tk.rottencucumber.backend.record.simple.SimpleRecord;
import tk.rottencucumber.backend.record.simple.SimpleRecordBuilder;
import tk.rottencucumber.backend.util.Base64Encoder;

import java.util.ArrayList;
import java.util.List;

public class MovieRecordBuilder {
    public static MovieRecord create(MovieModel model) {
        List<SimpleRecord> platforms = new ArrayList<>();
        List<SimpleRecord> genres = new ArrayList<>();
        List<PersonRecord> actors = new ArrayList<>();
        List<PersonRecord> directors = new ArrayList<>();
        List<PersonRecord> writers = new ArrayList<>();
        for (GenreModel item : model.getGenres()) {
            genres.add(SimpleRecordBuilder.create(item));
        }
        for (PlatformModel item : model.getPlatforms()) {
            platforms.add(SimpleRecordBuilder.create(item));
        }
        for (ActorModel item : model.getActors()) {
            actors.add(PersonRecordBuilder.create(item));
        }
        for (DirectorModel item : model.getDirectors()) {
            directors.add(PersonRecordBuilder.create(item));
        }
        for (WriterModel item : model.getWriters()) {
            writers.add(PersonRecordBuilder.create(item));
        }
        return new MovieRecord(
                model.getId(),
                model.getName(),
                model.getSlug(),
                model.getPreview(),
                model.getSummary(),
                Base64Encoder.encode(model.getImage()),
                model.getType(),
                model.getViews(),
                model.getRelease(),
                SimpleRecordBuilder.create(model.getLanguage()),
                genres,
                platforms,
                actors,
                directors,
                writers
        );
    }
}
