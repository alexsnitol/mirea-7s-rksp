package org.example;

import java.time.LocalTime;

import static java.util.Objects.nonNull;

public class FileProcessor {

    private final FileTypeEnum processingFileType;
    private final FileQueue fileQueue;

    public FileProcessor(FileTypeEnum processingFileType, FileQueue fileQueue) {
        this.processingFileType = processingFileType;
        this.fileQueue = fileQueue;
    }

    public void start() {
        while (true) {
            File file = fileQueue.peek();
            if (nonNull(file) && file.getType().equals(processingFileType)) {
                try {
                    System.out.println(LocalTime.now() + " " + processingFileType + " PROCESSOR: take file " + file + " for processing");
                    fileQueue.remove(file);
                    Thread.sleep(file.getSize() * 7L);
                    System.out.println(LocalTime.now() + " " + processingFileType + " PROCESSOR: file " + file + " has ben processed");
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

}
