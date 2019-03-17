import com.google.gson.Gson;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class Main {

    private static String JSON_FILE_PATH = "src/main/resources/jsonFile.json";
    private static String EXTERNALIZATION_FILE_PATH = "src/main/resources/extFile.txt";
    private static String SERIALIZATION_FILE_PATH = "src/main/resources/serializeFile.txt";

    public static void main(String[] args) throws ParseException {

        Person person = new Person(
                "Adam",
                "Adamowicz",
                new SimpleDateFormat("dd/mm/yyyy").parse("11/11/2000"),
                19);

        PersonExt personExt = new PersonExt(
                "Adam",
                "Adamowicz",
                new SimpleDateFormat("dd/mm/yyyy").parse("11/11/2000"),
                19);


        System.out.println(person);

        serializePersonToJson(person);
        System.out.println("Person is serialized to Json");

        serializeToFile(person);
        System.out.println("Person is serialized to File");

        customPersonExternalization(personExt);
        System.out.println("Person is externalized to txt file");

        Person personDeserializedFromJson = deserializePersonFromJson();
        System.out.println("Person deserialized from json:");
        System.out.println(personDeserializedFromJson);

        Person personDeserializedFromFile = deserializeFromFile();
        System.out.println("Person deserialized from file:");
        System.out.println(personDeserializedFromFile);

        PersonExt personDeexternalizedFromFile = customPersonDeexternalization();
        System.out.println("Person deexternalized from file:");
        System.out.println(personDeexternalizedFromFile);

        displaySizes();
    }

    private static void serializeToFile(Person person) {
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(new File(SERIALIZATION_FILE_PATH)));
            outputStream.writeObject(person);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Person deserializeFromFile() {

        try {
            ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(new File(SERIALIZATION_FILE_PATH)));
            return (Person) inputStream.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static void serializePersonToJson(Person person) {

        Gson gson = new Gson();
        try {
            FileWriter fileWriter = new FileWriter(JSON_FILE_PATH);
            gson.toJson(person, fileWriter);
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Person deserializePersonFromJson() {
        Gson gson = new Gson();
        FileReader fileReader;
        try {
            fileReader = new FileReader(JSON_FILE_PATH);
            final Person person = gson.fromJson(fileReader, Person.class);
            fileReader.close();
            return person;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static void customPersonExternalization(PersonExt person) {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(EXTERNALIZATION_FILE_PATH);
            ObjectOutputStream outputStream = new ObjectOutputStream(fileOutputStream);
            outputStream.writeObject(person);
            outputStream.close();
            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static PersonExt customPersonDeexternalization() {
        try {
            FileInputStream fileInputStream = new FileInputStream(EXTERNALIZATION_FILE_PATH);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            return (PersonExt) objectInputStream.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static void displaySizes() {

        File jsonFile = new File(JSON_FILE_PATH);
        File txtExternalizedFile = new File(EXTERNALIZATION_FILE_PATH);
        File txtFile = new File(SERIALIZATION_FILE_PATH);

        long jsonFileSize = jsonFile.length();
        long txtFileSize = txtFile.length();
        long externalizationFileSize = txtExternalizedFile.length();

        System.out.println("json file size: " + jsonFileSize);
        System.out.println("externalization file size: " + externalizationFileSize);
        System.out.println("txt file size: " + txtFileSize);

//        if (jsonFileSize > externalizationFileSize) {
//            System.out.println("json file is larger than txt file");
//        } else if (jsonFileSize < externalizationFileSize) {
//            System.out.println("json file is smaller than txt file");
//        } else {
//            System.out.println("files are equal in size");
//        }
    }
}
