package com.fenchurchtech.messaging.twitter.interfaces;

public interface INotification {
    void setMessage(String messageBody);
    String getMessage();

    void setStatus(String status);
    String getStatus ();
}
