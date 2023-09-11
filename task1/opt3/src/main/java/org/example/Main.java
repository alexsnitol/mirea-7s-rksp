package org.example;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {

    public static void main(String[] args) {
        FileQueue fileQueue = new FileQueue();
        FileGenerator fileGenerator = new FileGenerator(fileQueue);
        FileProcessor xmlFileProcessor = new FileProcessor(FileTypeEnum.XML, fileQueue);
        FileProcessor jsonFileProcessor = new FileProcessor(FileTypeEnum.JSON, fileQueue);
        FileProcessor xlsFileProcessor = new FileProcessor(FileTypeEnum.XLS, fileQueue);

        ExecutorService service = Executors.newFixedThreadPool(4);
        service.submit(fileGenerator::start);
        service.submit(xmlFileProcessor::start);
        service.submit(jsonFileProcessor::start);
        service.submit(xlsFileProcessor::start);
    }

}