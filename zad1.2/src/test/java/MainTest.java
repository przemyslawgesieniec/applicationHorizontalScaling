import org.apache.commons.lang3.time.StopWatch;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;

class MainTest {

    private static String FILE_PATH = "src/main/resources/largeFile.txt";
    private static int FILE_SIZE_MB = 800;
    private StopWatch stopWatch;
    private static List<String> results;

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
    void measureBufferedInputStreamReadPerformance() throws IOException {

        stopWatch.start();

        final BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(new File(FILE_PATH)));

        while (bufferedInputStream.read() != -1) {
        }

        stopWatch.stop();
        long readingTime = stopWatch.getTime();
        prettyOutput("BufferedInputStream", readingTime);
        bufferedInputStream.close();
    }

    @Test
    void measureBufferedReadReadPerformance() throws IOException {

        stopWatch.start();

        final BufferedReader bufferedReader = new BufferedReader(new FileReader(new File(FILE_PATH)));

        while (bufferedReader.read() != -1) {
        }

        stopWatch.stop();
        long readingTime = stopWatch.getTime();
        prettyOutput("BufferedRead", readingTime);
    }

    @Test
    void measureMemoryMappedFileReadPerformance() throws IOException {
        stopWatch.start();

        final File file = new File(FILE_PATH);
        final long fileSize = file.length();
        RandomAccessFile memoryFile = new RandomAccessFile(file, "r");
        MappedByteBuffer mappedByteBuffer = memoryFile.getChannel().map(FileChannel.MapMode.READ_ONLY, 0, fileSize);

        System.out.println(mappedByteBuffer.isLoaded());

        while(mappedByteBuffer.position() < fileSize){
            mappedByteBuffer.get();
        }

        stopWatch.stop();
        long readingTime = stopWatch.getTime();
        prettyOutput("BufferedRead", readingTime);
    }


    private void prettyOutput(final String methodName, final long timeRun) {

        String result = methodName + " | " + "file size: " + FILE_SIZE_MB + "MB | " + "time: " + timeRun + " ms";
        results.add(result);
    }


}