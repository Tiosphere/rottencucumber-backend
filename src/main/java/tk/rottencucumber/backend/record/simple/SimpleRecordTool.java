package tk.rottencucumber.backend.record.simple;

import tk.rottencucumber.backend.model.GenreModel;
import tk.rottencucumber.backend.model.LanguageModel;
import tk.rottencucumber.backend.model.PlatformModel;

import java.util.ArrayList;
import java.util.List;

public class SimpleRecordTool {
    public static List<SimpleRecord> createRecordsWithGen(Iterable<GenreModel> all) {
        List<SimpleRecord> list = new ArrayList<>();
        for (GenreModel model : all) {
            list.add(SimpleRecordBuilder.create(model));
        }
        return list;
    }

    public static List<SimpleRecord> createRecordsWithLang(Iterable<LanguageModel> all) {
        List<SimpleRecord> list = new ArrayList<>();
        for (LanguageModel model : all) {
            list.add(SimpleRecordBuilder.create(model));
        }
        return list;
    }

    public static List<SimpleRecord> createRecordsWithPlat(Iterable<PlatformModel> all) {
        List<SimpleRecord> list = new ArrayList<>();
        for (PlatformModel model : all) {
            list.add(SimpleRecordBuilder.create(model));
        }
        return list;
    }
}
