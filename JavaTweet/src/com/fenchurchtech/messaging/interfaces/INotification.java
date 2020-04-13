package com.fenchurchtech.messaging.interfaces;

public interface INotification {
    void setMessage(String messageBody);
    String getMessage();

    void setStatus(String status);
    String getStatus ();
}
