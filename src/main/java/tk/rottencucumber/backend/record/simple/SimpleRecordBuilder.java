package tk.rottencucumber.backend.record.simple;

import tk.rottencucumber.backend.model.GenreModel;
import tk.rottencucumber.backend.model.LanguageModel;
import tk.rottencucumber.backend.model.PlatformModel;

public class SimpleRecordBuilder {

    public static SimpleRecord create(GenreModel model) {
        return new SimpleRecord(model.getName(), model.getSlug());
    }

    public static SimpleRecord create(LanguageModel model) {
        return new SimpleRecord(model.getName(), model.getSlug());
    }

    public static SimpleRecord create(PlatformModel model) {
        return new SimpleRecord(model.getName(), model.getSlug());
    }
}
