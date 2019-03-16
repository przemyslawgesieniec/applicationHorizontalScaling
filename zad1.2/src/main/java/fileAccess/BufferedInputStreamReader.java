package fileAccess;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class BufferedInputStreamReader implements BenchmarkFileReader {

    @Override
    public void readFile(String filePath) throws IOException {

        final BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(new File(filePath)));
        while (bufferedInputStream.read() != -1) {
        }
        bufferedInputStream.close();
    }
}
