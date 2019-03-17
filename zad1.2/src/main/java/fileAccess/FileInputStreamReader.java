package fileAccess;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class FileInputStreamReader implements BenchmarkFileReader {
    @Override
    public void readFile(String filePath) throws IOException {

        InputStream inputStream = new FileInputStream(new File(filePath));
        while (inputStream.read() != -1) {
        }
        inputStream.close();
    }
}
