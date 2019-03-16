import fileAccess.BenchmarkFileReader;
import fileAccess.BufferedInputStreamReader;
import fileAccess.BufferedRead;
import fileAccess.NioReader;
import fileAccess.MemoryMappedFileReader;
import org.apache.commons.lang3.time.StopWatch;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

class MainTest {

    private static String FILE_PATH = "src/main/resources/largeFile.txt";
    private static int FILE_SIZE_MB = 800;
    private StopWatch stopWatch;
    private static List<String> results;

    private BenchmarkFileReader benchmarkFileReader;

    @BeforeEach
    void setUp() {
        stopWatch = new StopWatch();
    }

    @BeforeAll
    static void beforeAll() {
        Main.generateLargeTxtFile(FILE_SIZE_MB, FILE_PATH);
        results = new ArrayList<>();
    }

    @AfterEach
    void tearDown() {
        stopWatch.reset();
    }

    @AfterAll
    static void printResults() {
        results.forEach(System.out::println);
    }

    @Test
    void benchmarkBufferedInputStreamReadPerformance() throws IOException {

        stopWatch.start();

        benchmarkFileReader = new BufferedInputStreamReader();
        benchmarkFileReader.readFile(FILE_PATH);

        stopWatch.stop();
        long readingTime = stopWatch.getTime();
        prettyOutput("BufferedInputStream", readingTime);
    }

    @Test
    void benchmarkBufferedReadReadPerformance() throws IOException {

        stopWatch.start();

        benchmarkFileReader = new BufferedRead();
        benchmarkFileReader.readFile(FILE_PATH);

        stopWatch.stop();
        long readingTime = stopWatch.getTime();
        prettyOutput("BufferedRead", readingTime);
    }

    @Test
    void benchmarkMemoryMappedFileReadPerformance() throws IOException {
        stopWatch.start();

        benchmarkFileReader = new MemoryMappedFileReader();
        benchmarkFileReader.readFile(FILE_PATH);

        stopWatch.stop();

        long readingTime = stopWatch.getTime();
        prettyOutput("MemoryMappedFileReade", readingTime);
    }

    @Test
    void benchmarkFileChannelReadPerformance() throws IOException {
        stopWatch.start();

        benchmarkFileReader = new NioReader();
        benchmarkFileReader.readFile(FILE_PATH);

        stopWatch.stop();

        long readingTime = stopWatch.getTime();
        prettyOutput("NioReader", readingTime);
    }


    private void prettyOutput(final String methodName, final long timeRun) {

        String result = methodName + " | " + "file size: " + FILE_SIZE_MB + "MB | " + "time: " + timeRun + " ms";
        results.add(result);
    }


}