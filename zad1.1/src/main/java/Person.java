import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import java.io.Serializable;
import java.util.Date;

@Getter
@AllArgsConstructor
@ToString
@NoArgsConstructor
public class Person implements Serializable {

    private String firstName;
    private String lastName;
    private Date birthday;
    private int age;

}
