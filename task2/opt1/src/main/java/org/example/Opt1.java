package org.example;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.URISyntaxException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Objects;

/**
 * Создать файл формата .txt, содержащий несколько строк текста.
 * С помощью пакета java.nio нужно прочитать содержимое файла и вывести данные в стандартный поток вывода.
 */

public class Opt1 {
    public static void main(String[] args) throws IOException, URISyntaxException {
        RandomAccessFile file = new RandomAccessFile(
                new File(Objects.requireNonNull(Opt1.class.getClassLoader().getResource("test.txt")).toURI()),
                "r"
        );
        FileChannel channel = file.getChannel();
        ByteBuffer buffer = ByteBuffer.allocate(512);
        while (channel.read(buffer) > 0) {
            buffer.flip();
            while (buffer.hasRemaining()) {
                System.out.print((char) buffer.get());
            }
            buffer.clear();
        }
        channel.close();
        file.close();
    }
}