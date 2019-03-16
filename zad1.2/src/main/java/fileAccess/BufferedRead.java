package fileAccess;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class BufferedRead implements BenchmarkFileReader {
    @Override
    public void readFile(String filePath) throws IOException {

        final BufferedReader bufferedReader = new BufferedReader(new FileReader(new File(filePath)));
        while (bufferedReader.read() != -1) {
        }
        bufferedReader.close();
    }
}
