package com.grishberg.data.models.response;

import lombok.Data;

/**
 * Created by grishberg on 21.12.16.
 */
@Data
public class RestError {
    private final int code;
    private final String message;
}
