package org.example;


import io.reactivex.Observer;
import io.reactivex.subjects.PublishSubject;

public class Opt1 {

    public static void main(String[] args) {
        Observer<Signal> signalSubscriber = new Signaling();
        TemperatureProducer temperatureProducer = new TemperatureProducer();
        CO2Producer co2Producer = new CO2Producer();

        PublishSubject<Signal> subject = PublishSubject.create();
        subject.subscribe(signalSubscriber);

        temperatureProducer.start(subject);
        co2Producer.start(subject);
    }

}