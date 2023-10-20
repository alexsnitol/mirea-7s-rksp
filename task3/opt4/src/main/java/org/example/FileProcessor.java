package org.example;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

import java.time.LocalTime;
import java.util.concurrent.LinkedBlockingQueue;

import static java.util.Objects.nonNull;

public class FileProcessor implements Observer<File> {

    private final FileTypeEnum processingFileType;
    private final LinkedBlockingQueue<File> fileQueue;

    public FileProcessor(FileTypeEnum processingFileType, LinkedBlockingQueue<File> fileQueue) {
        this.processingFileType = processingFileType;
        this.fileQueue = fileQueue;
    }


    @Override
    public void onSubscribe(@NonNull Disposable d) {

    }

    @Override
    public void onNext(@NonNull File file) {
        file = fileQueue.peek();
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

    @Override
    public void onError(@NonNull Throwable e) {

    }

    @Override
    public void onComplete() {

    }
}
