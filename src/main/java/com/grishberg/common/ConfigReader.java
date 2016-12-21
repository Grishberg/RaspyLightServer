package com.grishberg.common;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;

/**
 * Created by grishberg on 20.12.16.
 */
public class ConfigReader {
    public static ConfigContext readConfig() {
        final ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(new File("cfg//config.json"), ConfigContext.class);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
