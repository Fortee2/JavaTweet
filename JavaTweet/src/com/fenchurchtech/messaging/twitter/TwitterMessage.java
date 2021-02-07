package com.fenchurchtech.messaging.twitter;

import com.fenchurchtech.messaging.twitter.interfaces.INotification;

public class TwitterMessage implements INotification {
    private String _message ="";
    private String _status = "";

    @Override
    public void setMessage(String messageBody) {
        _message = messageBody;
    }

    @Override
    public String getMessage() {
        return _message;
    }

    @Override
    public void setStatus(String status) {
        _status = status;
    }

    @Override
    public String getStatus() {
        return _status;
    }
}
