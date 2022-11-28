package tk.rottencucumber.backend.record.category;

import tk.rottencucumber.backend.model.GenreModel;
import tk.rottencucumber.backend.model.LanguageModel;
import tk.rottencucumber.backend.model.PlatformModel;

public class CategoryRecordWithIdBuilder {

    public static CategoryRecordWithID create(GenreModel model) {
        return new CategoryRecordWithID(model.getId(), model.getName(), model.getSlug());
    }

    public static CategoryRecordWithID create(LanguageModel model) {
        return new CategoryRecordWithID(model.getId(), model.getName(), model.getSlug());
    }

    public static CategoryRecordWithID create(PlatformModel model) {
        return new CategoryRecordWithID(model.getId(), model.getName(), model.getSlug());
    }
}
