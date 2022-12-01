package tk.rottencucumber.backend.record.person;

import tk.rottencucumber.backend.model.ActorModel;
import tk.rottencucumber.backend.model.DirectorModel;
import tk.rottencucumber.backend.model.WriterModel;

import java.util.ArrayList;
import java.util.List;

public class PersonRecordTool {
    public static List<PersonRecord> createRecordsWithAct(Iterable<ActorModel> all) {
        List<PersonRecord> list = new ArrayList<>();
        for (ActorModel model : all) {
            list.add(PersonRecordBuilder.create(model));
        }
        return list;
    }

    public static List<PersonRecord> createRecordsWithWri(Iterable<WriterModel> all) {
        List<PersonRecord> list = new ArrayList<>();
        for (WriterModel model : all) {
            list.add(PersonRecordBuilder.create(model));
        }
        return list;
    }

    public static List<PersonRecord> createRecordsWithDir(Iterable<DirectorModel> all) {
        List<PersonRecord> list = new ArrayList<>();
        for (DirectorModel model : all) {
            list.add(PersonRecordBuilder.create(model));
        }
        return list;
    }

}
