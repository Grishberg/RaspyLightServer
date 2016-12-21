package com.grishberg.data.models.response;

import lombok.Getter;

/**
 * Created by grishberg on 21.12.16.
 * Модель ответа от сервера
 */
@Getter
public class RestResponse<T> {
    private T data;
    private RestError error;

    public RestResponse<T> setData(final T data) {
        this.data = data;
        this.error = null;
        return this;
    }

    public RestResponse<T> setError(final int code, final String message) {
        this.error = new RestError(code, message);
        this.data = null;
        return this;
    }

    public RestResponse<T> setError(final RestError error) {
        this.error = error;
        this.data = null;
        return this;
    }
}
