package org.example;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

import java.time.LocalTime;

import static java.util.Objects.nonNull;

public class FileProcessor implements Observer<File> {

    private final FileTypeEnum processingFileType;

    public FileProcessor(FileTypeEnum processingFileType) {
        this.processingFileType = processingFileType;
    }


    @Override
    public void onSubscribe(@NonNull Disposable d) {

    }

    @Override
    public void onNext(@NonNull File file) {
        if (nonNull(file) && file.getType().equals(processingFileType)) {
            try {
                System.out.println(LocalTime.now() + " " + processingFileType + " PROCESSOR: take file " + file + " for processing");
                Thread.sleep(file.getSize() * 70L);
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
