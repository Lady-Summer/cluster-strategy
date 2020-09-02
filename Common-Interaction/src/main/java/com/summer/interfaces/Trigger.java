package com.summer.interfaces;

import com.summer.connector.web.BaseResponse;
import com.summer.request.ClusterRequest;

public interface Trigger {

     BaseResponse<String> initiate(ClusterRequest request);

     BaseResponse<String> destroy(String clusterId);

}
