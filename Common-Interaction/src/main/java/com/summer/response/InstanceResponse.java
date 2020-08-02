package com.summer.response;

import com.summer.connector.web.BaseResponse;
import com.summer.enums.StatusCode;

public class InstanceResponse extends BaseResponse<StatusCode> {

    public InstanceResponse(int code, String message, StatusCode status) {
        super(code, message, status);
    }

    public StatusCode getStatus() {
        return data;
    }

    public void setStatus(StatusCode status) {
        this.data = status;
    }

}
