import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.Date;

@Getter
@AllArgsConstructor
@ToString
@NoArgsConstructor
public class PersonExt implements Externalizable {

    private String firstName;
    private String lastName;
    private Date birthday;
    private int age;

    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeUTF(firstName);
        out.writeUTF(lastName);
        out.writeShort(age);
        out.writeLong(birthday.getTime());
    }

    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {

        firstName = in.readUTF();
        lastName = in.readUTF();
        age = in.readShort();
        birthday = new Date(in.readLong());
    }
}
