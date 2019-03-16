package fileAccess;

import java.io.IOException;

public interface BenchmarkFileReader {

    public void readFile(String filePath) throws IOException;
}
