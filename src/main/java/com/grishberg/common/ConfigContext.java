package com.grishberg.common;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.grishberg.data.models.User;
import freemarker.template.Configuration;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by grishberg on 20.12.16.
 */
@Getter
@Setter
@JsonIgnoreProperties({"templateConfiguration"})
public class ConfigContext {
    private Configuration templateConfiguration;
    private int port;
    private int lightPin;
    User[] users;
}
