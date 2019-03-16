package fileAccess;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

public class MemmoryMappedFileReader implements BenchmarkFileReader {

    @Override
    public void readFile(String filePath) throws IOException {

        final File file = new File(filePath);
        final long fileSize = file.length();
        RandomAccessFile memoryFile = new RandomAccessFile(file, "r");
        MappedByteBuffer mappedByteBuffer = memoryFile.getChannel().map(FileChannel.MapMode.READ_ONLY, 0, fileSize);

        while (mappedByteBuffer.position() < fileSize) {
            mappedByteBuffer.get();
        }
    }
}
