package org.example;

import java.time.LocalTime;
import java.util.Random;

public class FileGenerator {

    private final FileQueue fileQueue;

    public FileGenerator(FileQueue fileQueue) {
        this.fileQueue = fileQueue;
    }

    public void start() {
        while (true) {
            FileTypeEnum fileType = generateFileType();
            int fileSize = generateFileSize();
            File file = new File(fileType, fileSize);
            try {
                fileQueue.add(file);
                System.err.println(LocalTime.now() + " GENERATOR: generated file " + file + ", it order is " + fileQueue.size());
            } catch (FileQueueHasMaxSizeException ignored) {
            }
            try {
                Thread.sleep(new Random().nextInt(100, 1001));
            } catch (InterruptedException e) {
                throw new RuntimeException();
            }
        }
    }

    private int generateFileSize() {
        return new Random().nextInt(10, 101);
    }

    private FileTypeEnum generateFileType() {
        int rndFileTypeIndex = new Random().nextInt(0, FileTypeEnum.values().length);
        return FileTypeEnum.values()[rndFileTypeIndex];
    }

}
