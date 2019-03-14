import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.util.Random;

public class Main {


    public static void main(String[] args) {

       // generateLargeTxtFile(800);


    }

    public static void generateLargeTxtFile(int desireFileSizeInMB, String filePath) {
        try {
            File file = new File(filePath);
            Random random = new Random();
            FileWriter fileWriter = new FileWriter(file);
            FileOutputStream fileOutputStream = new FileOutputStream(file);

            while (file.length() < desireFileSizeInMB * 1024 * 1024){
                byte[] bytes = new byte[16384];
                random.nextBytes(bytes);
                fileOutputStream.write(bytes);
            }

            fileOutputStream.close();
            fileWriter.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
