package com.summer.request;

import java.io.Serializable;

public abstract class BaseRequest<T> implements Serializable {

    private T data;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
