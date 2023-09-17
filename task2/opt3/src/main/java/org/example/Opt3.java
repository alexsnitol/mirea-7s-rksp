package org.example;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Objects;

import static java.lang.System.err;
import static java.lang.System.out;

/**
 * Реализовать функцию нахождения 16-битной контрольной суммы файла с использованием бинарных операций и ByteBuffer.
 */

public class Opt3 {
    private static int sum(ByteBuffer bb) {
        int sum = 0;
        while (bb.hasRemaining()) {
            if ((sum & 1) != 0)
                sum = (sum >> 1) + 0x8000;
            else
                sum >>= 1;
            sum += bb.get() & 0xff;
            sum &= 0xffff;
        }
        return sum;
    }

    // Compute and print a checksum for the given file

    public static void sum(File f) throws IOException {

        // Open the file and then get a channel from the stream
        try (
                FileInputStream fis = new FileInputStream(f);
                FileChannel fc = fis.getChannel()) {

            // Get the file's size and then map it into memory
            int sz = (int) fc.size();
            MappedByteBuffer bb = fc.map(FileChannel.MapMode.READ_ONLY, 0, sz);

            // Compute and print the checksum
            int sum = sum(bb);
            int kb = (sz + 1023) / 1024;
            String s = Integer.toString(sum);
            out.println(s + "\t" + kb + "\t" + f);
        }
    }

    public static void main(String[] args) throws URISyntaxException {
        File f = new File(Objects.requireNonNull(Opt3.class.getClassLoader().getResource("test.txt")).toURI());
        try {
            sum(f);
        } catch (IOException e) {
            err.println(f + ": " + e);
        }
    }

}