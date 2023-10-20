package org.example;

import io.reactivex.subjects.Subject;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static java.lang.System.out;

public class TemperatureProducer {

    public void start(Subject<Signal> subject) {
        ExecutorService service = Executors.newSingleThreadExecutor();
        service.execute(() -> {
            while (true) {
                Signal signal = new Signal(
                        new Random().nextInt(15, 35),
                        SignalTypeEnum.TEMPERATURE
                );
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                out.println("Отправил значение " + signal);
                subject.onNext(signal);
            }
        });
        service.shutdown();
    }

}
