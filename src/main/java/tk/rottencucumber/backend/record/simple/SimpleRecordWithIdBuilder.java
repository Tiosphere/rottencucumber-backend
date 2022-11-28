package tk.rottencucumber.backend.record.simple;

import tk.rottencucumber.backend.model.GenreModel;
import tk.rottencucumber.backend.model.LanguageModel;
import tk.rottencucumber.backend.model.PlatformModel;

public class SimpleRecordWithIdBuilder {

    public static SimpleRecordWithID create(GenreModel model) {
        return new SimpleRecordWithID(model.getId(), model.getName(), model.getSlug());
    }

    public static SimpleRecordWithID create(LanguageModel model) {
        return new SimpleRecordWithID(model.getId(), model.getName(), model.getSlug());
    }

    public static SimpleRecordWithID create(PlatformModel model) {
        return new SimpleRecordWithID(model.getId(), model.getName(), model.getSlug());
    }
}
