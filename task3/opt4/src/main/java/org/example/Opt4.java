package org.example;

import io.reactivex.subjects.PublishSubject;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

public class Opt4 {

    public static void main(String[] args) {
        LinkedBlockingQueue<File> fileQueue = new LinkedBlockingQueue<>(5);
        FileGenerator fileGenerator = new FileGenerator(fileQueue);
        FileProcessor xmlFileProcessor = new FileProcessor(FileTypeEnum.XML, fileQueue);
        FileProcessor jsonFileProcessor = new FileProcessor(FileTypeEnum.JSON, fileQueue);
        FileProcessor xlsFileProcessor = new FileProcessor(FileTypeEnum.XLS, fileQueue);

        PublishSubject<File> subject = PublishSubject.create();
        subject.subscribe(xmlFileProcessor);
        subject.subscribe(jsonFileProcessor);
        subject.subscribe(xlsFileProcessor);

        ExecutorService service = Executors.newFixedThreadPool(2);
        service.execute(() -> fileGenerator.start(subject));
        service.shutdown();
    }

}