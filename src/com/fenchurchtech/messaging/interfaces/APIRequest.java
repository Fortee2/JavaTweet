package com.fenchurchtech.messaging.interfaces;

public interface APIRequest {

    String getEndPoint();
    void setEndpoint(String endpoint);

    INotification SendNotification(INotification message) throws Exception;

}
