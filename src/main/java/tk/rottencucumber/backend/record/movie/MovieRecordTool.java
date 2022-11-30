package tk.rottencucumber.backend.record.movie;

import tk.rottencucumber.backend.model.MovieModel;

import java.util.ArrayList;
import java.util.List;

public class MovieRecordTool {

    public static List<MovieRecord> getMovieRecords(Iterable<MovieModel> all) {
        List<MovieRecord> list = new ArrayList<>();
        for (MovieModel model : all) {
            list.add(MovieRecordBuilder.create(model));
        }

        return list;
    }
}
