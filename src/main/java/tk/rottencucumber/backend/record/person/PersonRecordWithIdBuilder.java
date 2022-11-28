package tk.rottencucumber.backend.record.person;

import tk.rottencucumber.backend.model.ActorModel;
import tk.rottencucumber.backend.model.DirectorModel;
import tk.rottencucumber.backend.model.WriterModel;
import tk.rottencucumber.backend.util.Base64Encoder;

public class PersonRecordWithIdBuilder {

    public static PersonRecordWithID create(ActorModel model) {
        return new PersonRecordWithID(model.getId(), model.getName(), model.getSlug(), Base64Encoder.encode(model.getImage()), model.getType());
    }

    public static PersonRecordWithID create(WriterModel model) {
        return new PersonRecordWithID(model.getId(), model.getName(), model.getSlug(), Base64Encoder.encode(model.getImage()), model.getType());
    }

    public static PersonRecordWithID create(DirectorModel model) {
        return new PersonRecordWithID(model.getId(), model.getName(), model.getSlug(), Base64Encoder.encode(model.getImage()), model.getType());
    }
}
