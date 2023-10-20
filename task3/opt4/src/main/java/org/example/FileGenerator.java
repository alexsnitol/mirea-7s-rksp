package org.example;

import io.reactivex.subjects.Subject;

import java.time.LocalTime;
import java.util.Random;
import java.util.concurrent.LinkedBlockingQueue;

public class FileGenerator {

    private final LinkedBlockingQueue<File> fileQueue;

    public FileGenerator(LinkedBlockingQueue<File> fileQueue) {
        this.fileQueue = fileQueue;
    }


    public void start(Subject<File> subject) {
        while (true) {
            FileTypeEnum fileType = generateFileType();
            int fileSize = generateFileSize();
            File file = new File(fileType, fileSize);
            try {
                fileQueue.add(file);
                System.err.println(LocalTime.now() + " GENERATOR: generated file " + file + ", it order is " + fileQueue.size());
                subject.onNext(file);
            } catch (IllegalStateException ignored) {
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
