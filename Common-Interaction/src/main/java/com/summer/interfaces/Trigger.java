package com.summer.interfaces;

import com.summer.connector.web.BaseResponse;
import com.summer.request.ClusterRequest;

public interface Trigger {

     BaseResponse<String> initiate(ClusterRequest request);

     BaseResponse<String> add(ClusterRequest clusterRequest, int clusterId);

     BaseResponse<String> destroy(int clusterId);

}
