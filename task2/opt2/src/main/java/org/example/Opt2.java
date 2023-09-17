package org.example;

import org.apache.commons.io.FileUtils;

import java.io.*;
import java.net.URISyntaxException;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;

import static java.lang.System.out;

/**
 * Реализовать копирование файла размером 100 Мб 4 методами:
 * 1) FileInputStream/FileOutputStream
 * 2) FileChannel
 * 3) Apache Commons IO
 * 4) Files class Замерить затраты по времени и памяти и провести сравнительный анализ.
 */

public class Opt2 {

    public static void main(String[] args) throws IOException, URISyntaxException {
        File sourceFile = new File(
                Objects.requireNonNull(Opt2.class.getClassLoader().getResource("CiscoPacketTracer.exe")).toURI()
        );
        File destFile = new File(
                Paths.get("opt2", "src", "main", "resources").toFile().getAbsolutePath() + "/CiscoPacketTracer-Copy.exe"
        );

        long startMs = System.currentTimeMillis();
        copyFileViaFileStream(sourceFile, destFile);
        long endMs = System.currentTimeMillis();
        out.println("File Streams method: " + (endMs - startMs) + " ms");
        FileUtils.delete(destFile);

        startMs = System.currentTimeMillis();
        copyFileViaFileChannel(sourceFile, destFile);
        endMs = System.currentTimeMillis();
        out.println("File Channel method: " + (endMs - startMs) + " ms");
        FileUtils.delete(destFile);

        startMs = System.currentTimeMillis();
        copyFileViaApacheCommonsIO(sourceFile, destFile);
        endMs = System.currentTimeMillis();
        out.println("Apache Commons IO method: " + (endMs - startMs) + " ms");
        FileUtils.delete(destFile);

        startMs = System.currentTimeMillis();
        copyFileViaFilesClass(sourceFile, destFile);
        endMs = System.currentTimeMillis();
        out.println("Files class method: " + (endMs - startMs) + " ms");
        FileUtils.delete(destFile);
    }

    public static void copyFileViaFileStream(File sourceFile, File destFile) throws IOException {
        try (
                InputStream is = new FileInputStream(sourceFile);
                OutputStream os = new FileOutputStream(destFile)
        ) {
            byte[] buffer = new byte[1024];
            int length;
            while ((length = is.read(buffer)) > 0) {
                os.write(buffer, 0, length);
            }
        }
    }

    public static void copyFileViaFileChannel(File sourceFile, File destFile) throws IOException {
        try (
                FileChannel sourceChannel = new FileInputStream(sourceFile).getChannel();
                FileChannel destChannel = new FileOutputStream(destFile).getChannel()
        ) {
            destChannel.transferFrom(sourceChannel, 0, sourceChannel.size());
        }
    }

    public static void copyFileViaApacheCommonsIO(File sourceFile, File destFile) throws IOException {
        FileUtils.copyFile(sourceFile, destFile);
    }

    public static void copyFileViaFilesClass(File sourceFile, File destFile) throws IOException {
        Files.copy(sourceFile.toPath(), destFile.toPath());
    }

}