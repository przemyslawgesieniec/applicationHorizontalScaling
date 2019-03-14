import org.apache.commons.lang3.time.StopWatch;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

class MainTest {

    private static String FILE_PATH = "src/main/resources/largeFile.txt";
    private static int FILE_SIZE_MB = 800;
    private StopWatch stopWatch;

    @BeforeEach
    void setUp() {
        stopWatch = new StopWatch();
    }

    @BeforeAll
    static void beforeAll() {
        Main.generateLargeTxtFile(FILE_SIZE_MB, FILE_PATH);
    }

    @AfterEach
    void tearDown() {
        stopWatch.reset();
    }

    @Test
    void measureBufferedInputStreamReadPerformance() throws IOException {

        stopWatch.start();

        final File file = new File(FILE_PATH);
        final FileInputStream fileInputStream = new FileInputStream(file);
        final BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);

        while (bufferedInputStream.read() != -1) {
        }

        stopWatch.stop();
        long readingTime = stopWatch.getTime();
        prettyOutput("BufferedInputStream", readingTime);
        bufferedInputStream.close();
        fileInputStream.close();
    }


    private void prettyOutput(final String methodName, final long timeRun) {
        System.out.println(methodName + " | " + "file size: " + FILE_SIZE_MB + "MB | " + "time: " + timeRun + " ms");

    }

}