package org.example;

import io.reactivex.Observable;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

public class Opt4 {

    public static void main(String[] args) {
        LinkedBlockingQueue<File> fileQueue = new LinkedBlockingQueue<>(5);
        FileGenerator fileGenerator = new FileGenerator(fileQueue);
        FileProcessor xmlFileProcessor = new FileProcessor(FileTypeEnum.XML);
        FileProcessor jsonFileProcessor = new FileProcessor(FileTypeEnum.JSON);
        FileProcessor xlsFileProcessor = new FileProcessor(FileTypeEnum.XLS);

        Observable<File> fileObservable = nonBlockingFileObservable(fileQueue);
        fileObservable.subscribe(xmlFileProcessor);
        fileObservable.subscribe(jsonFileProcessor);
        fileObservable.subscribe(xlsFileProcessor);

        ExecutorService generatorService = Executors.newFixedThreadPool(4);
        generatorService.execute(fileGenerator::start);

        generatorService.shutdown();
    }

    private static Observable<File> nonBlockingFileObservable(LinkedBlockingQueue<File> fileQueue) {
        return Observable.create(subscriber -> {
            ExecutorService transportService = Executors.newFixedThreadPool(4);
            transportService.execute(() -> {
                while (true) {
                    try {
                        subscriber.onNext(fileQueue.take());
                    } catch (InterruptedException ignored) {
                    }
                }
            });
        });
    }

}