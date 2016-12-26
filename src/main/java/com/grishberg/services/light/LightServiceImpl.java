package com.grishberg.services.light;

import com.grishberg.common.ConfigContext;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.pi4j.io.gpio.*;

/**
 * Created by grishberg on 22.12.16.
 */
public class LightServiceImpl implements LightService {
    private Map<Integer, Boolean> state;
    private final ConfigContext configContext;
    private final GpioController gpio;
    private final GpioPinDigitalOutput lightPin;

    public LightServiceImpl(ConfigContext configContext) {
        this.configContext = configContext;
        this.state = new ConcurrentHashMap<>();

        // create gpio controller
        gpio = GpioFactory.getInstance();
        lightPin = gpio.provisionDigitalOutputPin(
                RaspiPin.getPinByAddress(configContext.getLightPin()), "MyLED", PinState.LOW);
        // set shutdown state for this pin
        lightPin.setShutdownOptions(true, PinState.LOW);
    }

    @Override
    public boolean checkState(int port) {
        return lightPin.isHigh();
    }

    @Override
    public void changeState(int port, boolean enable) {
        if (enable) {
            lightPin.high();
        } else {
            lightPin.low();
        }
    }

    @Override
    public void stop() {
        gpio.shutdown();
    }
}
