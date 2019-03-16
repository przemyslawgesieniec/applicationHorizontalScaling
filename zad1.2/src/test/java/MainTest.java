import fileAccess.BenchmarkFileReader;
import fileAccess.BufferedInputStreamReader;
import fileAccess.BufferedRead;
import fileAccess.MemmoryMappedFileReader;
import org.apache.commons.lang3.time.StopWatch;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

class MainTest {

    private static final int ALLOCATION_BLOCK_SIZE = 512;
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

        benchmarkFileReader = new MemmoryMappedFileReader();
        benchmarkFileReader.readFile(FILE_PATH);

        stopWatch.stop();

        long readingTime = stopWatch.getTime();
        prettyOutput("MemoryMappedFileReade", readingTime);
    }


    @Test
    void benchmarkNewBufferedReadReadPerformance() throws IOException {

        stopWatch.start();

        final BufferedReader bufferedReader = Files.newBufferedReader(Paths.get(FILE_PATH), Charset.defaultCharset());

//        while (bufferedReader.read() != -1) {
//        }
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            System.out.println(line);

        }
        stopWatch.stop();
        long readingTime = stopWatch.getTime();
        prettyOutput("NewBufferedRead", readingTime);
    }

//
//    @Test
//    void benchmarkChannelRead() {
//        stopWatch.start();
//
//        File file = new File(FILE_PATH);
//        try {
//            InputStream inputStream = new FileInputStream(file);
//            ReadableByteChannel byteChannel = Channels.newChannel(inputStream);
//            ByteBuffer byteBuffer = ByteBuffer.allocate(ALLOCATION_BLOCK_SIZE);
//
//todo verify if ok
//            while (byteChannel.read(byteBuffer) > 0) {
//                byteBuffer.flip();
//                while (byteBuffer.hasRemaining()) {
//                    char ch = (char) byteBuffer.get();
//                }
//            }
//
//            stopWatch.stop();
//            long readingTime = stopWatch.getTime();
//            prettyOutput("ChannelReader", readingTime);
//
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//

    private void prettyOutput(final String methodName, final long timeRun) {

        String result = methodName + " | " + "file size: " + FILE_SIZE_MB + "MB | " + "time: " + timeRun + " ms";
        results.add(result);
    }


}