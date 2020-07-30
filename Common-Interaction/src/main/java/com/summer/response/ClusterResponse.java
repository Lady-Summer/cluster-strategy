package com.summer.response;

import com.summer.connector.web.BaseResponse;

public class ClusterResponse extends BaseResponse<InstanceResponse> {

    private int id;

    private String clusterName;

    public ClusterResponse(int code, String message, InstanceResponse instance) {
        super(code, message, instance);
    }

    public ClusterResponse(int code, String message, int id, String clusterName, InstanceResponse instance) {
        super(code, message, instance);
        this.id = id;
        this.clusterName = clusterName;
    }

    public String getClusterName() {
        return clusterName;
    }

    public void setClusterName(String clusterName) {
        this.clusterName = clusterName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
