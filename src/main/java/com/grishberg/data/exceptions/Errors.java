package com.grishberg.data.exceptions;

import com.grishberg.data.models.response.RestError;

/**
 * Created by grishberg on 21.12.16.
 */
public final class Errors {
    public static final RestError AUTH_ERROR = new RestError(1000, "Bad login or password");
    public static final RestError AUTH_EMPTY_FIELDS_ERROR = new RestError(1001, "Empty login or password");
    public static final RestError NOT_AUTHORIZED = new RestError(1002, "Not authorized");
}
