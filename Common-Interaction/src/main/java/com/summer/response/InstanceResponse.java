package com.summer.response;

import com.summer.enums.StatusCode;

public class InstanceResponse extends BaseResponse {

    private StatusCode status;

    public InstanceResponse(int code, String message, StatusCode status) {
        super(code, message);
        this.status = status;
    }

    public StatusCode getStatus() {
        return status;
    }

    public void setStatus(StatusCode status) {
        this.status = status;
    }

    public InstanceResponse(int code, String message) {
        super(code, message);
    }
}
