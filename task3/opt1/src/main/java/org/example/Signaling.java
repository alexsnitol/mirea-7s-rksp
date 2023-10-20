package org.example;


import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

import static java.lang.System.err;
import static java.lang.System.out;
import static org.example.SignalTypeEnum.C02;
import static org.example.SignalTypeEnum.TEMPERATURE;

public class Signaling implements Observer<Signal> {

    private static final int TEMPERATURE_MAX = 25;
    private static final int C02_MAX = 70;

    private volatile int lastTemperature = 0;
    private volatile int lastC02 = 0;


    @Override
    public void onSubscribe(@NonNull Disposable d) {

    }

    @Override
    public void onNext(Signal signal) {
        if (TEMPERATURE.equals(signal.type())) {
            if (signal.value() > TEMPERATURE_MAX && lastC02 > C02_MAX) {
                err.println("ALARM!!!");
            } else {
                if (signal.value() > TEMPERATURE_MAX) {
                    out.println("повышенная температура: " + signal.value());
                }
            }
            lastTemperature = signal.value();
        } else if (C02.equals(signal.type())) {
            if (signal.value() > C02_MAX && lastTemperature > TEMPERATURE_MAX) {
                err.println("ALARM!!!");
            } else {
                if (signal.value() > C02_MAX) {
                    out.println("повышенный CO2: " + signal.value());
                }
            }
            lastC02 = signal.value();
        }
    }

    @Override
    public void onError(Throwable e) {

    }

    @Override
    public void onComplete() {

    }

}
