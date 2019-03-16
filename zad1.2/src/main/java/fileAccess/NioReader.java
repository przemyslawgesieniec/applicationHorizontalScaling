package fileAccess;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class NioReader implements BenchmarkFileReader {

    @Override
    public void readFile(String filePath) throws IOException {


        RandomAccessFile randomAccessFile = new RandomAccessFile(filePath, "r");
        FileChannel fileChannel = randomAccessFile.getChannel();

        ByteBuffer byteBuffer = ByteBuffer.allocate(2048);

        int bytesRead = fileChannel.read(byteBuffer);
        while (bytesRead != -1) {
            byteBuffer.flip();
            while(byteBuffer.hasRemaining()){
                byteBuffer.get();
            }
            byteBuffer.clear();
            bytesRead = fileChannel.read(byteBuffer);
        }
        randomAccessFile.close();

    }
}
