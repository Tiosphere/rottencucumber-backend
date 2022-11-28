package tk.rottencucumber.backend.record.category;

import tk.rottencucumber.backend.model.GenreModel;
import tk.rottencucumber.backend.model.LanguageModel;
import tk.rottencucumber.backend.model.PlatformModel;

public class CategoryRecordBuilder {

    public static CategoryRecord create(GenreModel model) {
        return new CategoryRecord(model.getName(), model.getSlug());
    }

    public static CategoryRecord create(LanguageModel model) {
        return new CategoryRecord(model.getName(), model.getSlug());
    }

    public static CategoryRecord create(PlatformModel model) {
        return new CategoryRecord(model.getName(), model.getSlug());
    }
}
