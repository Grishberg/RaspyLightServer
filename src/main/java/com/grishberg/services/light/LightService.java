package com.grishberg.services.light;

/**
 * Created by grishberg on 22.12.16.
 */
public interface LightService {
    boolean checkState();

    void changeState(boolean enable);
}
