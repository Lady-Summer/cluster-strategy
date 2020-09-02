package com.summer.response;

import com.summer.connector.web.BaseResponse;

public class CloudResponse extends BaseResponse<ClusterResponse> {

    public CloudResponse(int code, String message, ClusterResponse data) {
        super(code, message, data);
    }
}
