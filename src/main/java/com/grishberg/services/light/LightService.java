package com.grishberg.services.light;

/**
 * Created by grishberg on 22.12.16.
 */
public interface LightService {
    boolean checkState(int port);

    void changeState(int port, boolean enable);

    void stop();
}
