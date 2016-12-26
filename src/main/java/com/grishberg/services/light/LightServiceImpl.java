package com.grishberg.services.light;

import com.grishberg.common.ConfigContext;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by grishberg on 22.12.16.
 */
public class LightServiceImpl implements LightService {
    private Map<Integer, Boolean> state;
    private final ConfigContext configContext;

    public LightServiceImpl(ConfigContext configContext) {
        this.configContext = configContext;
        this.state = new ConcurrentHashMap<>();
        state.put(0, false);
    }

    @Override
    public boolean checkState(int port) {
        return state.get(port);
    }

    @Override
    public void changeState(int port, boolean enable) {
        state.put(port, enable);
    }
}
