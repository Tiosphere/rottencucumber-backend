package tk.rottencucumber.backend.record.person;

import tk.rottencucumber.backend.model.ActorModel;
import tk.rottencucumber.backend.model.DirectorModel;
import tk.rottencucumber.backend.model.WriterModel;
import tk.rottencucumber.backend.util.Base64Encoder;

public class PersonRecordBuilder {

    public static PersonRecord create(ActorModel model) {
        return new PersonRecord(model.getName(), model.getSlug(), Base64Encoder.encode(model.getImage()), model.getType());
    }

    public static PersonRecord create(DirectorModel model) {
        return new PersonRecord(model.getName(), model.getSlug(), Base64Encoder.encode(model.getImage()), model.getType());
    }

    public static PersonRecord create(WriterModel model) {
        return new PersonRecord(model.getName(), model.getSlug(), Base64Encoder.encode(model.getImage()), model.getType());
    }
}
